package gyb.securefiletransfer.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gyb.securefiletransfer.common.handler.exceptionhandler.MyException;
import gyb.securefiletransfer.entity.Directory;
import gyb.securefiletransfer.mapper.DirectoryMapper;
import gyb.securefiletransfer.service.DirectoryService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
@Service
public class DirectoryServiceImpl extends ServiceImpl<DirectoryMapper, Directory> implements DirectoryService {
    /**
     * 获取目录文件
     * @param directoryName 文件名称
     * @return 目录类列表
     */
    @Override
    public List<Directory> getDirectories(String directoryName) {
        List<Directory> directories = new ArrayList<>();
        try {
            if ("/".equals(directoryName)){
                for (File file : File.listRoots()) {
                    //将系统盘转化为Directory
                    Directory directory = getDirectory(file);
                    directories.add(directory);
                }
            }else {
                java.io.File fileNamePath = new File(directoryName);
                //排除隐藏文件
                for (File file : Objects.requireNonNull(fileNamePath.listFiles( ((dir, name)  -> !name.startsWith(".")) ))) {
                    //将文件转化为Directory
                    Directory directory = getDirectory(directoryName, file);
                    if (directory == null) continue;
                    directories.add(directory);
                }
            }
        }catch (Exception e){
            throw new MyException(20001,"暂无权限");
        }
        return directories;
    }

    @Override
    public Directory createDirectory(String directoryName, String parentDirectoryPath,Integer userId) {
        File newDirectory = new File(parentDirectoryPath, directoryName);
        if (!newDirectory.exists()) {
            // 创建文件夹
            boolean created = newDirectory.mkdir();
            if (created) {
                //转化为Directory类
                return toDirectory(newDirectory,parentDirectoryPath,userId);
            }
        } else {
            throw new MyException(20001,"目录已经存在");
        }
        return null;
    }

    private Directory toDirectory(File newDirectory, String parentDirectoryPath,Integer userId) {;
        Directory directory = new Directory();
        directory.setIsDirectory(true);
        directory.setDirectoryName(newDirectory.getName());
        directory.setDirectoryPath(newDirectory.getPath());
        directory.setParentDirectoryName(parentDirectoryPath);
        directory.setCreatedAt(new Date());
        directory.setOwnerId(userId);
        return directory;
    }

    /**
     * 获取非根目录
     * @param directoryName 传入文件名
     * @param file 转化后文件对象
     * @return 非根目录文件
     */
    private Directory getDirectory(String directoryName, File file) {
        Directory directory = new Directory();
        directory.setOwnerId(1);
        directory.setIsDirectory(file.isDirectory());
        String[] split = file.toString().split("\\\\");
        String dirName = split[split.length - 1];
        //排除隐藏文件
        if ("System Volume Information".equals(dirName)||
                "$RECYCLE.BIN".equals(dirName)||
                "chunk".equals(dirName)||
                "Documents and Settings".equals(dirName)||
                "$Recycle.Bin".equals(dirName)||
                "PerfLogs".equals(dirName)||
                "Recovery".equals(dirName)){
            return null;
        }
        directory.setDirectoryName(dirName);
        directory.setDirectoryPath(file.getPath().replace("\\\\","\\"));
        //directory.setDirectoryPath(file.getPath().replace("\\","//"));
        directory.setParentDirectoryName(directoryName.replace("\\\\","\\"));
        directory.setCreatedAt(new Date(file.lastModified()));
        //文件则 设置大小
        if (!directory.getIsDirectory()){
            directory.setSize(file.length());
        }
        return directory;
    }

    /**
     * 获取根目录
     * @param file 文件名称
     * @return 根目录盘
     */
    private Directory getDirectory(File file) {
        Directory directory = new Directory();
        directory.setOwnerId(1);
        directory.setDirectoryName(file.toString().split("\\\\")[0]);
        directory.setDirectoryPath(file.getPath());
        directory.setIsDirectory(true);
        directory.setParentDirectoryName("/");
        directory.setCreatedAt(new Date(file.lastModified()));
        return directory;
    }

}
