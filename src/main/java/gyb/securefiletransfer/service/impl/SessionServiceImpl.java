package gyb.securefiletransfer.service.impl;

import gyb.securefiletransfer.entity.Session;
import gyb.securefiletransfer.mapper.SessionMapper;
import gyb.securefiletransfer.service.SessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
