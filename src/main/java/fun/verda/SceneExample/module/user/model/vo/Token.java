package fun.verda.SceneExample.module.user.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Token implements Serializable {
    private static final long serialVersionUID = -23641931690837200L;

    private String token;
}
