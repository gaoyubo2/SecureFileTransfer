package gyb.securefiletransfer.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

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
public class FileVO implements Serializable {


    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "文件ID")
    private Integer fileId;

    @ApiModelProperty(value = "存储路径")
    private String storagePath;

    @ApiModelProperty(value = "第几块")
    private Integer chunkNumber;

    @ApiModelProperty(value = "分块文件")
    private MultipartFile file;



}
