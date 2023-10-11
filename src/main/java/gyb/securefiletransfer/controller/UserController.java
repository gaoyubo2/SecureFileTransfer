package gyb.securefiletransfer.controller;


import gyb.securefiletransfer.common.utils.Result;
import gyb.securefiletransfer.entity.Session;
import gyb.securefiletransfer.entity.User;
import gyb.securefiletransfer.service.SessionService;
import gyb.securefiletransfer.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user != null) {
            // 创建会话
            Session session = sessionService.startSession(user);
            return Result.ok().message("Login success").data("Session",session);
        } else {
            return Result.error().message("Login failed. Invalid credentials.");
        }
    }

}

