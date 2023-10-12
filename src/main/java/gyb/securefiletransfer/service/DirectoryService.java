package gyb.securefiletransfer.service;


import com.baomidou.mybatisplus.extension.service.IService;
import gyb.securefiletransfer.entity.Directory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
@Mapper
public interface DirectoryService extends IService<Directory> {
    /**
     * 获取系统盘
     * @param directoryName 文件名称
     * @return 该文件的下一层子文件
     */
    List<Directory> getDirectories(String directoryName);

    /**
     * 创建目录
     * @param directoryName 目录名称
     * @param parentDirectoryPath 父目录路径
     * @return 目录对象
     */
    Directory createDirectory(String directoryName, String parentDirectoryPath,Integer userId);
}
