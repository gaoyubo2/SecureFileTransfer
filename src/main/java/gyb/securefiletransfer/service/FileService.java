package gyb.securefiletransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import gyb.securefiletransfer.entity.File;
import gyb.securefiletransfer.entity.vo.Chunk;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
public interface FileService extends IService<File> {
    /**
     * 初始化文件
     * @param originalFilename 文件
     * @param size 文件大小
     * @return 文件对象
     */
    File initiateFileUpload(String originalFilename, long size, Integer userId);

    Map<String, Object> checkChunkExits(Chunk chunk);

    Integer saveChunk(Integer chunkNumber, String identifier);

    void mergeFile(String fileName, String s, String relativePath) throws IOException;

    Integer uploadChunk(Chunk chunk);
}
