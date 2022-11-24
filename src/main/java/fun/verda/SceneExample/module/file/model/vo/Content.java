package fun.verda.SceneExample.module.file.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "请求体内容")
public class Content implements Serializable {

    private static final long serialVersionUID = -33641951690837200L;

    @Schema(description = "base64字符串")
    private String img;
}
