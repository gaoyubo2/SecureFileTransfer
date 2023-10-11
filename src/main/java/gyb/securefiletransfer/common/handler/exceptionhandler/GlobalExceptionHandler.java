package gyb.securefiletransfer.common.handler.exceptionhandler;

import gyb.securefiletransfer.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 郜宇博

 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     * @param e
     * @return 全局异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理");
    }
    /**
     * 特定异常处理
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("执行了算数异常");
    }
    /**
     * 自定义异常
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result error(MyException e){
        log.error(e.getMessage() );
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
}
