package gyb.securefiletransfer.controller;


import gyb.securefiletransfer.common.utils.JwtUtil;
import gyb.securefiletransfer.common.utils.Result;
import gyb.securefiletransfer.entity.Session;
import gyb.securefiletransfer.entity.User;
import gyb.securefiletransfer.service.SessionService;
import gyb.securefiletransfer.service.UserService;
import org.springframework.web.bind.annotation.*;

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
    public Result login(@RequestBody User nameAndPwd) {
        System.out.println(nameAndPwd);
        //验证
        User user = userService.login(nameAndPwd.getUsername(), nameAndPwd.getPassword());
        if (user != null) {
            // 创建会话
            Session session = sessionService.startSession(user);
            return Result.ok().message("Login success").data("Session",session);
        } else {
            return Result.error().message("Login failed. Invalid credentials.");
        }
    }


    // 其他操作，如文件分享、文件操作日志、文件访问权限等

}

