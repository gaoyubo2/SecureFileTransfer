package gyb.securefiletransfer.controller;


import gyb.securefiletransfer.common.handler.exceptionhandler.MyException;
import gyb.securefiletransfer.entity.Directory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import gyb.securefiletransfer.common.utils.Result;
import gyb.securefiletransfer.entity.File;
import gyb.securefiletransfer.entity.vo.Chunk;
import gyb.securefiletransfer.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    private final static String CHUNK_FOLDER = "/Users/gaoyubo/resource/data/chunk";

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/start")
    public Result startUpload(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {
        // 在开始上传前，执行一些初始化操作，例如创建文件记录
        File fileMetaData = fileService.initiateFileUpload(file.getOriginalFilename(), file.getSize(), 1);
        return Result.ok().data("file", fileMetaData);
    }

    @GetMapping("chunk")
    public Map<String, Object> checkChunks(Chunk chunk) {
        return fileService.checkChunkExits(chunk);
    }

    @PostMapping("chunk")
    public Map<String, Object> saveChunk(Chunk chunk) {
        Integer chunks = fileService.uploadChunk(chunk);
        Map<String, Object> result = new HashMap<>();
        if (chunks.equals(chunk.getTotalChunks())) {
            result.put("message","上传成功！");
            result.put("code", 205);
        }
        return result;
    }

    @PostMapping("merge")
    public Result mergeChunks(Chunk chunk) {
        String fileName = chunk.getFilename();
        try {
            fileService.mergeFile(fileName, CHUNK_FOLDER + java.io.File.separator + chunk.getIdentifier(),chunk.getRelativePath());
            return Result.ok().message("上传文件成功");
        } catch (Exception e) {
            return Result.error().message("暂无权限");
        }
    }
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String directoryPath) {
        try {
            // 构建文件路径
            Path path = Paths.get(directoryPath);
            Resource resource = new UrlResource(path.toUri());
            String filename = resource.getFilename();
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+ URLEncoder.encode(filename, "UTF-8"))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 处理 URL 异常
            throw new MyException(20001,"下载失败");
        }
    }
}

