package gyb.securefiletransfer.service.impl;

import gyb.securefiletransfer.common.utils.IPUtils;
import gyb.securefiletransfer.common.utils.JwtUtil;
import gyb.securefiletransfer.entity.Session;
import gyb.securefiletransfer.entity.User;
import gyb.securefiletransfer.mapper.SessionMapper;
import gyb.securefiletransfer.service.SessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
@Service
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session> implements SessionService {
    /**
     * 开始一段会话
     *
     * @param user    用户信息
     * @param request http请求
     * @return 会话信息
     */
    public Session startSession(User user, HttpServletRequest request) {
        // 创建一个新的会话
        Session session = new Session();
        // 生成JWT令牌
        String jwtToken = JwtUtil.getJwtToken(user.getUserId(), user.getUsername());

        // 设置会话属性
        session.setUserId(user.getUserId());
        session.setSessionToken(jwtToken);
        session.setLoginTime(new Date());
        session.setIpAddress(IPUtils.getIpAddr(request));

        //持久化session信息
        baseMapper.insert(session);

        return session;
    }
    /**
     * 退出登录
     *
     * @param sessionId 会话Id
     */
    @Override
    public void logout(Integer sessionId) {
        Session session = new Session();
        session.setSessionId(sessionId);
        session.setLogoutTime(new Date());
        baseMapper.updateById(session);
    }



}
