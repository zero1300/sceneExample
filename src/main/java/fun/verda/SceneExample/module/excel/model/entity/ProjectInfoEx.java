package fun.verda.SceneExample.module.excel.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ProjectInfoEx {

    @ExcelProperty(value = "日期")
    private String date;

    @ExcelProperty(value = "描述")
    private String desc;
}
