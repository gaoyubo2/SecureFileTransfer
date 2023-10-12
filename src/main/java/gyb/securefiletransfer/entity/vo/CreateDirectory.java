package gyb.securefiletransfer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @Date 2023/10/12 9:39
 * @Author 郜宇博
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CreateDirectory对象")
public class CreateDirectory {

    @ApiModelProperty(value = "目录名称")
    private String directoryName;

    @ApiModelProperty(value = "父目录路径")
    private String parentDirectoryPath;
}
