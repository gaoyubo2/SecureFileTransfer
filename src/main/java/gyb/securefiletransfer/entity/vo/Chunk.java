package gyb.securefiletransfer.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

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
@ApiModel(value = "File对象")
public class Chunk implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件ID")
    private Integer fileId;

    @ApiModelProperty(value = "第几块")
    private Integer chunkNumber;

    @ApiModelProperty(value = "分块大小")
    private Long chunkSize;

    @ApiModelProperty(value = "当前分块大小")
    private Long currentChunkSize;

    @ApiModelProperty(value = "总文件大小")
    private Long totalSize;

    @ApiModelProperty(value = "文件标识")
    private String identifier;

    @ApiModelProperty(value = "文件名称")
    private String filename;

    @ApiModelProperty(value = "相对路径")
    private String relativePath;

    @ApiModelProperty(value = "总块数")
    private Integer totalChunks;

    @ApiModelProperty(value = "分块文件")
    @JSONField(serialize = false)
    private MultipartFile file;
}
