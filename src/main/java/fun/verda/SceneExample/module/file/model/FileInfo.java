package fun.verda.SceneExample.module.file.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "file")
public class FileInfo implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String filename;

    private String relPath;

    private int isDel;
}
