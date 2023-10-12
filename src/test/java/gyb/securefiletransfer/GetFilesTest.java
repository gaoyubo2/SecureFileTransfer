package gyb.securefiletransfer;

import gyb.securefiletransfer.entity.Directory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date 2023/10/11 21:32
 * @Author 郜宇博
 */
public class GetFilesTest {
    @Test
    public void run(){

    }

    public void listFilesAndDirectories(File directory, List<Directory> result, Integer parentDirectoryId) {
        String rootPath = "C:\\";  // 指定要浏览的根路径，可以根据需求更改
        List<Directory> directories = new ArrayList<>();

        // 调用递归方法来构建文件和目录的列表
        listFilesAndDirectories(new File(rootPath), directories, null);

        // 打印获取的文件和目录
        for (Directory dir : directories) {
            System.out.println(dir);
        }

        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                Directory dir = new Directory();
                dir.setDirectoryName(file.getName());
                dir.setCreatedAt(new Date(file.lastModified()));
                dir.setOwnerId(1); // 设置所有者ID，可以根据需求设置
                //dir.setParentDirectoryName(parentDirectoryId); // 设置父目录ID

                if (file.isDirectory()) {
                    // 如果是目录，递归处理子目录
                    listFilesAndDirectories(file, result, dir.getDirectoryId());
                }

                result.add(dir);
            }
        }
        System.out.println(result);
    }

}
