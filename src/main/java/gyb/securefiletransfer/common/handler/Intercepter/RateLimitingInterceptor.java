package gyb.securefiletransfer.common.handler.Intercepter;

import com.google.common.annotations.Beta;
import com.google.common.util.concurrent.RateLimiter;
import gyb.securefiletransfer.common.utils.JwtUtil;
import gyb.securefiletransfer.entity.User;
import gyb.securefiletransfer.mapper.UserMapper;
import gyb.securefiletransfer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Date 2023/10/16 11:09
 * @Author 郜宇博
 */
@Component
public class RateLimitingInterceptor implements HandlerInterceptor {
    private final RateLimiter rateLimiter5 = RateLimiter.create(30); // 每秒最多允许5次请求
    private final RateLimiter rateLimiter4 = RateLimiter.create(20); // 每秒最多允许4次请求
    private final RateLimiter rateLimiter3 = RateLimiter.create(10); // 每秒最多允许3次请求
    private final RateLimiter rateLimiter2 = RateLimiter.create(5); // 每秒最多允许2次请求
    private final RateLimiter rateLimiter1 = RateLimiter.create(1); // 每秒最多允许1次请求
    private final UserService userService;

    public RateLimitingInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("POST")){
            //获取vip等级
            Integer userId = JwtUtil.getMemberIdRequest(request);
            User user = new User();
            user.setUserId(userId);
            Integer vip  = userService.getById(userId).getVip();
            //限速
            rateLimitByVip(vip);

            return true;
        }
        return true;
    }

    /**
     * 限速
     * VIP 1 :每秒最多发1个请求
     * VIP 2 :每秒最多发5个请求
     * VIP 3 :每秒最多发10个请求
     * VIP 4 :每秒最多发20个请求
     * VIP 5 :每秒最多发30个请求
     * VIP>5 :无限制
     * @param vip vip等级
     */
    private void rateLimitByVip(Integer vip) {
        // 根据VIP等级进行限速
        if (1 == vip) {
            rateLimiter1.acquire();
        }else if (2 == vip){
            rateLimiter2.acquire();
        }else if (3 == vip){
            rateLimiter3.acquire();
        }else if (4 == vip){
            rateLimiter4.acquire();
        }else if (5 == vip){
            rateLimiter5.acquire();
        }
    }
}
