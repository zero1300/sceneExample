package fun.verda.SceneExample.module.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.verda.SceneExample.module.user.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
