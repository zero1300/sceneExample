package fun.verda.SceneExample.module.user.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "用户登录请求体")
public class UserLoginVo implements Serializable {
    private static final long serialVersionUID = -33641931699837200L;


    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "密码")
    private String password;
}
