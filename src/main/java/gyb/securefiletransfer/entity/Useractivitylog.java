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
@ApiModel(value="Useractivitylog对象", description="")
public class Useractivitylog implements Serializable {

    @Serial
    private static final long serialVersionUID=1L;

    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;

    private Integer userId;

    private String activity;

    private Date activityTime;


}
