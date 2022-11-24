package fun.verda.SceneExample.module.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -33641931690837200L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    private String nickname;

    @JsonIgnore
    private Long phone;

    @JsonIgnore
    private String password;
    
    private String avatar;

    // 0-未启用，1-已启用
    @JsonIgnore
    private Integer status;
}

