package gyb.securefiletransfer.controller;


import com.baomidou.mybatisplus.extension.api.R;
import gyb.securefiletransfer.common.utils.Result;
import gyb.securefiletransfer.entity.Session;
import gyb.securefiletransfer.entity.User;
import gyb.securefiletransfer.service.SessionService;
import gyb.securefiletransfer.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final SessionService sessionService;

    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody User nameAndPwd, HttpServletRequest request) {
        //验证
        User user = userService.login(nameAndPwd.getUsername(), nameAndPwd.getPassword());
        if (user != null) {
            // 创建会话
            Session session = sessionService.startSession(user,request);
            return Result.ok().message("登录成功").data("session",session).data("userInfo",user);
        } else {
            return Result.error().message("用户名或密码错误");
        }
    }
    @PostMapping("/logout")
    public Result logout(@RequestBody Integer sessionId) {
        //验证
        try {
            sessionService.logout(sessionId);
            return Result.ok().message("退出成功");
        } catch (Exception e) {
            return Result.error().message("退出失败");
        }

    }


    // 其他操作，如文件分享、文件操作日志、文件访问权限等

}

