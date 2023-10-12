package gyb.securefiletransfer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel(value="Filepermission对象")
public class Filepermission implements Serializable {

    
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "权限ID")
    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;

    @ApiModelProperty(value = "文件ID")
    private Integer fileId;

    @ApiModelProperty(value = "用户ID，与特定用户关联")
    private Integer userId;

    @ApiModelProperty(value = "角色ID，与特定角色关联")
    private Integer roleId;

    @ApiModelProperty(value = "是否具备查看权限")
    private Boolean canView;

    @ApiModelProperty(value = "是否具备编辑权限")
    private Boolean canEdit;

    @ApiModelProperty(value = "是否具备删除权限")
    private Boolean canDelete;


}
