package gyb.securefiletransfer.service.impl;

import gyb.securefiletransfer.common.handler.exceptionhandler.MyException;
import gyb.securefiletransfer.entity.File;
import gyb.securefiletransfer.mapper.FileMapper;
import gyb.securefiletransfer.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, gyb.securefiletransfer.entity.File> implements FileService {


    @Override
    public File initiateFileUpload(String originalFilename, long size, Integer userId) {

        // 创建文件元数据记录并插入到数据库
        File fileMetaData = new File();
        fileMetaData.setFileName(originalFilename);
        fileMetaData.setSize(size);
        fileMetaData.setOwnerId(userId);
        fileMetaData.setIsUploaded(false);
        fileMetaData.setUploadProgress(0L);
        fileMetaData.setCreatedAt(new Date());

        // 保存文件元数据到数据库
        baseMapper.insert(fileMetaData);

        // 返回文件标识
        return fileMetaData;

    }

    /**
     * 分块上传文件（默认一次上传100KB）
     *
     * @param fileId      文件Id
     * @param chunkNumber 块索引
     * @param bytes       文件数据
     * @param storagePath 存储位置
     * @return 是否完成上传
     */
    @Override
    public Boolean uploadFilePart(Integer fileId, Integer chunkNumber, byte[] bytes, String storagePath) {

        // 根据fileId和chunkNumber，确定文件块的存储路径
        String tempPath = "D://tempFileChunk";
        String fileChunkPath = tempPath + java.io.File.separator + fileId;

        // 检查是否已存在相同块号的文件块
        java.io.File fileChunk = new java.io.File(fileChunkPath + java.io.File.separator + chunkNumber);
        if (!fileChunk.exists()) {
            // 保存文件块
            try (FileOutputStream fos = new FileOutputStream(fileChunk)) {
                fos.write(bytes);
            } catch (Exception e) {
                throw new MyException(20001, "文件上传失败");
            }
        }

        // 更新文件上传进度
        updateUploadProgress(fileId, chunkNumber, bytes.length);
        // 文件是否完全上传
        if (isFileUploadComplete(fileId, chunkNumber)) {
            //整合块文件
            markFileAsUploaded(fileId, tempPath, storagePath);
            //删除临时块
            deleteTempChunks(tempPath);
            return true;
        }
        return false;
    }

    private void deleteTempChunks(String tempPath) {
        java.io.File tempDir = new java.io.File(tempPath);
        if (tempDir.exists() && tempDir.isDirectory()) {
            java.io.File[] chunkFiles = tempDir.listFiles();
            if (chunkFiles != null) {
                for (java.io.File chunkFile : chunkFiles) {
                    if (chunkFile.isFile()) {
                        chunkFile.delete();
                    }
                }
            }
            tempDir.delete();
        }

    }

    private void markFileAsUploaded(Integer fileId, String tempPath, String storagePath) {
        FileOutputStream storagePathStream;
        try {
            storagePathStream = new FileOutputStream(storagePath);
        } catch (Exception e) {
            throw new MyException(20001, "构件合并文件流失败");
        }
        int blockNumber = 0;
        java.io.File blockFile;
        while (true) {
            // 构建块文件的路径
            String blockFileName = tempPath + java.io.File.separator + fileId + java.io.File.separator + blockNumber;
            blockFile = new java.io.File(tempPath, blockFileName);
            if (blockFile.exists()) {
                // 读取块文件并追加到完整文件
                storageCompleteFile(storagePathStream, blockFile);
                blockNumber++;
            } else {
                // 块文件不存在，结束整合过程
                break;
            }
        }
        //更新IsUploaded
        successUpload(fileId);
    }


    private static void storageCompleteFile(FileOutputStream storagePathStream, java.io.File blockFile) {
        try (FileInputStream blockFileInputStream = new FileInputStream(blockFile)) {
            byte[] buffer = new byte[10240];
            int bytesRead;
            while ((bytesRead = blockFileInputStream.read(buffer)) != -1) {
                storagePathStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new MyException(20001, "合并文件");
        }
    }


    private void successUpload(Integer fileId) {
        File file = new File();
        file.setFileId(fileId);
        file.setIsUploaded(true);
        baseMapper.updateById(file);
    }

    private boolean isFileUploadComplete(Integer fileId, Integer chunkNumber) {
        //计算总共需要多少多少Chunk
        File file = baseMapper.selectById(fileId);
        // 102400字节 = 100KB
        Integer needChunk = Math.toIntExact(file.getSize() % 102400 == 0 ?
                file.getSize() / 102400 :
                file.getSize() / 102400 + 1);
        return needChunk.equals(chunkNumber);
    }

    private void updateUploadProgress(Integer fileId, Integer chunkNumber, int byteLength) {
        File file = new File();
        file.setFileId(fileId);
        //已经上传字节数
        Long uploadedBytes = (long) chunkNumber * byteLength;
        //块信息: 1,2,3,4,5。。。
        file.setUploadedChunks(chunkNumber + ",");
        file.setUploadProgress(uploadedBytes);
        baseMapper.updateById(file);
    }
}
