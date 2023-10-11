package gyb.securefiletransfer.service.impl;

import gyb.securefiletransfer.entity.User;
import gyb.securefiletransfer.mapper.UserMapper;
import gyb.securefiletransfer.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
