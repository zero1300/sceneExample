package fun.verda.SceneExample.interceptor;

import fun.verda.SceneExample.anno.NoNeedToken;
import fun.verda.SceneExample.constant.RedisConstant;
import fun.verda.SceneExample.module.user.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor  {

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {

        if (request.getRequestURI().contains("swagger") || request.getRequestURI().contains("api-docs")) {
            return true;
        }

        // 判断 Controller 上有没有 NoNeedToken 注解
        boolean noNeedToken = ((HandlerMethod) handler).getMethod().isAnnotationPresent(NoNeedToken.class);
        if (noNeedToken) {
            return true;
        }
        String token = request.getHeader("token");
        if (token == null) throw new RuntimeException("请上传token");
        String tokenKey = RedisConstant.REDIS_TOKEN_PRE + token;
        User user = redisTemplate.opsForValue().get(tokenKey);
        if (user == null) throw new RuntimeException("无效token");
        request.setAttribute("token", token);
        request.setAttribute("user", user);
        return true;
    }

}
