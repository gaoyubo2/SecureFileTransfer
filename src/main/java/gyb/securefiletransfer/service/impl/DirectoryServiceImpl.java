package gyb.securefiletransfer.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gyb.securefiletransfer.entity.Directory;
import gyb.securefiletransfer.mapper.DirectoryMapper;
import gyb.securefiletransfer.service.DirectoryService;
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
public class DirectoryServiceImpl extends ServiceImpl<DirectoryMapper, Directory> implements DirectoryService {

}
