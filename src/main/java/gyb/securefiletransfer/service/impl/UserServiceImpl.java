package gyb.securefiletransfer.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import gyb.securefiletransfer.entity.Session;
import gyb.securefiletransfer.entity.User;
import gyb.securefiletransfer.mapper.UserMapper;
import gyb.securefiletransfer.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    /**
     * 用户登陆
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    @Override
    public User login(String username, String password) {
        // 根据用户名从数据库中获取用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username); // 根据用户名查询
        User user = this.getOne(queryWrapper);

        if (user != null) {
            // 获取数据库中存储的哈希密码
            String hashedPassword = user.getPassword();
            // 进行密码验证
            if (isPasswordValid(password, hashedPassword)) {
                return user;
            }
        }

        return null;
    }




    /**
     * 密码验证
     * @param plainPassword 明文密码
     * @param hashedPassword md5后的密码（数据库中的密码）
     * @return 是否一致
     */
    private boolean isPasswordValid(String plainPassword, String hashedPassword) {
        // 这里执行密码验证逻辑，比较明文密码和哈希密码是否匹配
        String hashedInput = SecureUtil.md5(plainPassword);
        System.out.println(hashedInput);
        return hashedInput.equals(hashedPassword);
    }

}
