package gyb.securefiletransfer.service.impl;

import gyb.securefiletransfer.common.utils.JwtUtil;
import gyb.securefiletransfer.entity.Session;
import gyb.securefiletransfer.entity.User;
import gyb.securefiletransfer.mapper.SessionMapper;
import gyb.securefiletransfer.service.SessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
     * @param user 用户信息
     * @return 会话信息
     */
    public Session startSession(User user) {
        // 创建一个新的会话
        Session session = new Session();
        // 生成JWT令牌
        String jwtToken = JwtUtil.getJwtToken(user.getUserId(), user.getUsername());

        // 设置会话属性
        session.setSessionId(user.getUserId());
        session.setSessionToken(jwtToken);
        session.setLoginTime(new Date());

        // TODO 在此执行其他可能需要的操作，如存储会话信息到数据库或缓存中

        return session;
    }



}
