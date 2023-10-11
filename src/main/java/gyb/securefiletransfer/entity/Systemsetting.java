package gyb.securefiletransfer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
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
@ApiModel(value = "Systemsetting对象")
public class Systemsetting implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "setting_id", type = IdType.AUTO)
    private Integer settingId;

    private String settingName;

    private String settingValue;


}
