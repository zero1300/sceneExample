package fun.verda.SceneExample.module.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.verda.SceneExample.module.user.dao.UserDao;
import fun.verda.SceneExample.module.user.model.User;
import fun.verda.SceneExample.module.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
