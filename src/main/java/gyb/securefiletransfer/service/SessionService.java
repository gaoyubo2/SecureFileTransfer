package gyb.securefiletransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import gyb.securefiletransfer.entity.Session;
import gyb.securefiletransfer.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
public interface SessionService extends IService<Session> {
    /**
     * 开始一段会话
     * @param user 用户信息
     * @return 会话信息
     */
    Session startSession(User user);

}
