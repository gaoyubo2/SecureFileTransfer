package gyb.securefiletransfer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value="File对象")
public class File implements Serializable {

    
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "文件ID")
    @TableId(value = "file_id", type = IdType.AUTO)
    private Integer fileId;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    private Integer size;

    @ApiModelProperty(value = "存储路径")
    private String storagePath;

    @ApiModelProperty(value = "文件拥有者")
    private Integer ownerId;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;


}
