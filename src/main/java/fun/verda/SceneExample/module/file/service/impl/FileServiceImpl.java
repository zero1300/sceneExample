package fun.verda.SceneExample.module.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.verda.SceneExample.module.file.dao.FileDao;
import fun.verda.SceneExample.module.file.model.FileInfo;
import fun.verda.SceneExample.module.file.service.FileService;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl extends ServiceImpl<FileDao, FileInfo> implements FileService {
}
