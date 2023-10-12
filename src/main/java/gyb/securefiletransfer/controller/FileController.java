package gyb.securefiletransfer.controller;


import gyb.securefiletransfer.common.utils.JwtUtil;
import gyb.securefiletransfer.common.utils.Result;
import gyb.securefiletransfer.entity.Directory;
import gyb.securefiletransfer.entity.File;
import gyb.securefiletransfer.service.FileService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/start")
    public Result startUpload(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {
        // 在开始上传前，执行一些初始化操作，例如创建文件记录
        File fileMetaData = fileService.initiateFileUpload(file.getOriginalFilename(), file.getSize(), JwtUtil.getMemberIdRequest(httpServletRequest));
        return Result.ok().data("file", fileMetaData);
    }

    @PostMapping("/upload/{fileId}/{chunkNumber}")
    public Result uploadFilePart(
            @PathVariable Integer fileId,
            @PathVariable Integer chunkNumber,
            @RequestParam("storagePath") String storagePath,
            @RequestParam("file") MultipartFile filePart) {
        try {
            // 上传文件块
            Boolean flag = fileService.uploadFilePart(fileId, chunkNumber, filePart.getBytes(), storagePath);
            return flag ? Result.ok().message("全部上传成功") : Result.ok().message("未完全上传");
        } catch (Exception e) {
            return Result.error().message("上传失败");
        }
    }

    @GetMapping("/fileProgress/{fileId}")
    public Result resumeUploadProgress(@PathVariable Long fileId) {
        try {
            File file = fileService.getById(fileId);
            // 获取文件的上传进度
            Long uploadProgress = file.getUploadProgress();
            String[] chunks = file.getUploadedChunks().split(",");
            Integer chunkNumber = Integer.valueOf(chunks[chunks.length - 1]);

            return Result.ok().data("chunkNumber", chunkNumber).data("uploadProgress", uploadProgress);
        } catch (Exception e) {
            return Result.error().message("获取文件上传进度失败");
        }
    }


   /* @PostMapping("/complete/{fileId}")
    public Result completeUpload(@PathVariable String fileId) {
        // 标记文件上传完成 TODO
        Boolean isComplete = fileService.completeFileUpload(fileId);
        if (isComplete) {
            return Result.ok().message("上传文件成功");
        }
        return Result.error().message("文件上传失败");
    }

    @GetMapping("/progress/{fileId}")
    public Result getUploadProgress(@PathVariable String fileId) {
        // 获取文件上传进度 TODO
        long progress = fileService.getUploadProgress(fileId);
        return Result.ok().data("progress", progress);
    }*/

}

