package gyb.securefiletransfer.service.impl;

import gyb.securefiletransfer.common.handler.exceptionhandler.MyException;
import gyb.securefiletransfer.entity.File;
import gyb.securefiletransfer.entity.vo.Chunk;
import gyb.securefiletransfer.mapper.FileMapper;

import gyb.securefiletransfer.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import gyb.securefiletransfer.common.utils.RedisUtil;

import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;


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
    private final RedisUtil RedisUtil;
    //private static final String mergeFolder = "/Users/gaoyubo/resource/data/merge";
    private final static String CHUNK_FOLDER = "/Users/gaoyubo/resource/data/chunk";

    public FileServiceImpl(RedisUtil RedisUtil) {
        this.RedisUtil = RedisUtil;
    }


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

    @Override
    public Map<String, Object> checkChunkExits(Chunk chunk) {
        Map<String, Object> res = new HashMap<>();
        String identifier = chunk.getIdentifier();
        if (RedisUtil.existsKey(identifier)) {
            Set<Integer> chunkNumber = (Set<Integer>) RedisUtil.hmGet(identifier, "chunkNumberList");
            res.put("chunkNumbers", chunkNumber);
        }
        System.out.println(res);
        return res;
    }

    @Override
    public Integer saveChunk(Integer chunkNumber, String identifier) {
        Set<Integer> oldChunkNumber = (Set<Integer>) RedisUtil.hmGet(identifier, "chunkNumberList");
        if (Objects.isNull(oldChunkNumber)) {
            Set<Integer> newChunkNumber = new HashSet<>();
            newChunkNumber.add(chunkNumber);
            RedisUtil.hmSet(identifier, "chunkNumberList", newChunkNumber);
            return newChunkNumber.size();
        } else {
            oldChunkNumber.add(chunkNumber);
            RedisUtil.hmSet(identifier, "chunkNumberList", oldChunkNumber);
            return oldChunkNumber.size();
        }

    }

    @Override
    public void mergeFile(String fileName, String chunkFolder, String relativePath) throws IOException {
        Path mergePath = Paths.get(relativePath);
        System.out.println("-----");
        System.out.println(mergePath);
        if (!Files.isWritable(mergePath)) {
            Files.createDirectories(mergePath);
        }
        String target = relativePath + java.io.File.separator + fileName;
        Files.createFile(Paths.get(target));
        mergeList(chunkFolder, target);
    }

    private static void mergeList(String chunkFolder, String target) throws IOException {
        Files.list(Paths.get(chunkFolder))
                .filter(path -> path.getFileName().toString().contains("-"))
                .sorted(FileServiceImpl::sortChunk)
                .forEach(path -> {
                    mergeChunk(target, path);
                });
    }

    private static int sortChunk(Path o1, Path o2) {
        String p1 = o1.getFileName().toString();
        String p2 = o2.getFileName().toString();
        int i1 = p1.lastIndexOf("-");
        int i2 = p2.lastIndexOf("-");
        return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
    }

    private static void mergeChunk(String target, Path path) {
        try {
            //以追加的形式写入文件
            Files.write(Paths.get(target), Files.readAllBytes(path), StandardOpenOption.APPEND);
            //合并后删除该块
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer uploadChunk(Chunk chunk) {
        MultipartFile file = chunk.getFile();
        Integer chunkNumber = chunk.getChunkNumber();
        String identifier = chunk.getIdentifier();
        writeChunk(chunk, file);
        Integer chunks;
        //防止chunkNumber遗漏
        synchronized (FileServiceImpl.class) {
            chunks = saveChunk(chunkNumber, identifier);
            System.out.println(chunkNumber);
        }
        return chunks;
    }

    private static void writeChunk(Chunk chunk, MultipartFile file) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
            Path path = Paths.get(generatePath(CHUNK_FOLDER, chunk));
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new MyException(20001, "写入块失败");
        }
    }

    private static String generatePath(String uploadFolder, Chunk chunk) {

        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append(java.io.File.separator).append(chunk.getIdentifier());
        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        Path path = Paths.get(sb.toString());
        if (!Files.isWritable(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new MyException(20001, "生成path失败");
            }
        }
        return getChunkPath(chunk, sb);

    }

    private static String getChunkPath(Chunk chunk, StringBuilder sb) {
        return sb.append(java.io.File.separator)
                .append(chunk.getFilename())
                .append("-")
                .append(chunk.getChunkNumber()).toString();
    }


}
