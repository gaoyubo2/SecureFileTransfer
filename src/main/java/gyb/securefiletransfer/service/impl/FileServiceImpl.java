package gyb.securefiletransfer.service.impl;
import java.io.File;

import gyb.securefiletransfer.common.handler.exceptionhandler.MyException;
import gyb.securefiletransfer.entity.Directory;
import gyb.securefiletransfer.mapper.FileMapper;
import gyb.securefiletransfer.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class FileServiceImpl extends ServiceImpl<FileMapper, gyb.securefiletransfer.entity.File> implements FileService {


}
