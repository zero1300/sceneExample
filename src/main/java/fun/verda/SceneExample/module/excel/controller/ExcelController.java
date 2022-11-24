package fun.verda.SceneExample.module.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import fun.verda.SceneExample.module.excel.model.entity.ProjectInfoEx;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
@RequestMapping("/excel")
@Tag(name = "excel工具")
public class ExcelController {

    @Value("${file.local.path}")
    private String dir;

    @Operation(summary = "解析excel")
    @PostMapping("/parseExcel")
    public String parseExcel(MultipartFile file) {
        log.info("get in...");
        String path = dir + file.getOriginalFilename();
        File exFile = new File(path);
        if (!exFile.exists()){
            try {
                boolean ok = exFile.createNewFile();
                if (ok) {
                    file.transferTo(exFile);
                }
            }catch (Exception e) {
                log.error("新增文件：{} 失败", path);
                e.printStackTrace();
            }
        }
        EasyExcel.read(exFile, ProjectInfoEx.class, new PageReadListener<ProjectInfoEx>(rows -> {
            for (ProjectInfoEx row : rows) {
                String projectName = row.getDate();
                log.info("日期： {}, 描述： {}", projectName, row.getDesc());
            }
        })).sheet().doRead();
        return "ok";
    }
}
