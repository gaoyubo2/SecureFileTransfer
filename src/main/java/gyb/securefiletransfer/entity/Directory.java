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
@ApiModel(value="Directory对象")
public class Directory implements Serializable {

    @Serial
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "目录结构ID")
    @TableId(value = "directory_id", type = IdType.AUTO)
    private Integer directoryId;

    @ApiModelProperty(value = "目录结构名称")
    private String directoryName;

    @ApiModelProperty(value = "指向父目录的ID，根目录可以设置为NULL")
    private Integer parentDirectoryId;

    @ApiModelProperty(value = "目录的所有者")
    private Integer ownerId;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;


}
