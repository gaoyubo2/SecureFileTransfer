package gyb.securefiletransfer.common.handler.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 郜宇博
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyException extends RuntimeException{

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String msg;

}
