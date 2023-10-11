package gyb.securefiletransfer.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serial;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Session对象")
public class Session implements Serializable {

    @Serial
    private static final long serialVersionUID=1L;

    @TableId(value = "session_id", type = IdType.AUTO)
    private Integer sessionId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "会话Token")
    private String sessionToken;

    private String ipAddress;

    private Date loginTime;

    private Date logoutTime;


}
