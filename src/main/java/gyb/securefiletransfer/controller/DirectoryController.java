package gyb.securefiletransfer.controller;


import gyb.securefiletransfer.common.utils.JwtUtil;
import gyb.securefiletransfer.common.utils.Result;
import gyb.securefiletransfer.entity.Directory;
import gyb.securefiletransfer.entity.vo.CreateDirectory;
import gyb.securefiletransfer.service.DirectoryService;
import gyb.securefiletransfer.service.FileService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
@RestController
@RequestMapping("/directory")
public class DirectoryController {
    private final DirectoryService directoryService;

    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    /**
     * 获取目录列表
     * @param directoryName 目录文件名称
     * @return 目录列表
     */
    @GetMapping("/directories")
    public Result getDirectories(@RequestParam("directoryName") String directoryName){
        List<Directory> directories = directoryService.getDirectories(directoryName);
        if (directories != null){
            System.out.println(directories);
            return Result.ok().data("directories",directories);
        }
        return Result.error().message("获取系统盘失败");
    }

    //TODO 获取文件操作


    /**
     * 创建文件夹
     * @param createDirectory 文件名和父目录路径
     * @return 目录对象
     */
    @PostMapping("/directory")
    public Result createDirectory(@RequestBody CreateDirectory createDirectory, HttpServletRequest httpServletRequest) {
        // 创建新文件夹
        Directory directory = directoryService.createDirectory(createDirectory.getDirectoryName(), createDirectory.getParentDirectoryPath(), JwtUtil.getMemberIdRequest(httpServletRequest));
        if (directory != null){
            return Result.ok().data("directory",directory);
        }
        return Result.error().message("创建文件夹失败");
    }

    //@DeleteMapping("/delete-file")
    //public Result deleteFile(@RequestParam int fileId) {
    //    // 删除文件或文件夹
    //    boolean success = fileService.deleteFile(fileId);
    //    if (success) {
    //        return Result.ok().message("File deleted.");
    //    } else {
    //        return Result.error().message("File deletion failed.");
    //    }
    //}

    //@PostMapping("/upload-file")
    //public Result uploadFile(@RequestParam String fileName, @RequestParam int parentFolderId, @RequestBody byte[] fileData) {
    //    // 上传文件
    //    File uploadedFile = fileService.uploadFile(fileName, parentFolderId, fileData);
    //    return Result.ok().message("File uploaded. File ID: " + uploadedFile.getFileId());
    //}
    //
    //@GetMapping("/download-file")
    //public Result downloadFile(@RequestParam int fileId) {
    //    // 下载文件
    //    byte[] fileData = fileService.downloadFile(fileId);
    //    return Result.ok().data("fileData", fileData);
    //}

}

