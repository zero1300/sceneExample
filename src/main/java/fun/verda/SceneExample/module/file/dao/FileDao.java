package fun.verda.SceneExample.module.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.verda.SceneExample.module.file.model.FileInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDao extends BaseMapper<FileInfo> {
}
