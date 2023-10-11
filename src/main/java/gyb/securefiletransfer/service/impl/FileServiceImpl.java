package gyb.securefiletransfer.service.impl;

import gyb.securefiletransfer.entity.File;
import gyb.securefiletransfer.mapper.FileMapper;
import gyb.securefiletransfer.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 郜宇博
 * @since 2023-10-11
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

}
