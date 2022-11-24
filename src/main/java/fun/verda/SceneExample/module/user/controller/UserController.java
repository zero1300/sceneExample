package fun.verda.SceneExample.module.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.verda.SceneExample.anno.NoNeedToken;
import fun.verda.SceneExample.constant.RedisConstant;
import fun.verda.SceneExample.module.user.model.User;
import fun.verda.SceneExample.module.user.model.vo.Token;
import fun.verda.SceneExample.module.user.model.vo.UserLoginVo;
import fun.verda.SceneExample.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Tag(name = "用户模块")
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Operation(summary = "获得当前用户信息")
    @GetMapping("/getUserInfo")
    public User getUserInfo(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return userService.getById(user.getId());
    }


    @NoNeedToken
    @Operation(summary = "用户登录")
    @PostMapping ("/login")
    public Token login(@RequestBody UserLoginVo userLoginVo, HttpServletRequest req) {
        String phone = userLoginVo.getPhone();
        String password = userLoginVo.getPassword();
        User user = userService.getOne(new QueryWrapper<User>().eq("phone", phone).last("limit 1"));
        if (user == null) throw new RuntimeException("用户不存在");
        if (!Objects.equals(password, user.getPassword())) {
            return null;
        }
        String id = req.getSession().getId();
        String key = RedisConstant.REDIS_TOKEN_PRE + id;
        redisTemplate.opsForValue().set(key, user, 48, TimeUnit.HOURS);
        Token token = new Token();
        token.setToken(id);
        return token;
    }
}
