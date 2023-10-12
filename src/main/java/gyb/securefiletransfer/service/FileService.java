package gyb.securefiletransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import gyb.securefiletransfer.entity.File;

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

    /**
     * 分块上传文件
     *
     * @param fileId      文件Id
     * @param chunkNumber 块索引
     * @param bytes       文件数据
     * @param storagePath 存储位置
     * @return 是否完成上传
     */
    //Boolean uploadFilePart(Long fileId, Integer chunkNumber, byte[] bytes, String storagePath);

    Boolean uploadFilePart(Integer fileId, Integer chunkNumber, byte[] bytes, String storagePath);
}
