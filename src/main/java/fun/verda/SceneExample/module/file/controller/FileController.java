package fun.verda.SceneExample.module.file.controller;

import fun.verda.SceneExample.module.file.model.FileInfo;
import fun.verda.SceneExample.module.file.model.vo.Content;
import fun.verda.SceneExample.module.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;


@RestController
@Slf4j
@Tag(name = "文件模块")
@RequestMapping("file")
public class FileController {

    @Value("${file.local.path}")
    private String dir;

    @Resource
    private FileService fileService;

    @Operation(summary = "图片列表")
    @PostMapping("/imgList")
    public List<FileInfo> imgList() {
        return fileService.list();
    }

    @Operation(summary = "base64转成图片")
    @PostMapping("/uploadBase64Img")
    public String uploadBase64Img(@RequestBody Content content){
        String img = content.getImg();
        if (img == null) {
            return "error, empty img";
        }
        try {
            byte[] b = Base64.getDecoder().decode(img);
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            File tempFile = new File("D:\\123.png");
            OutputStream out = Files.newOutputStream(tempFile.toPath());
            out.write(b);
            out.flush();
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
            return "出现错误";
        }
        log.info("获得的base64为： {}", img);
        return "ok";
    }

    @Operation(summary = "上传图片")
    @PostMapping("/uploadFile")
    public FileInfo uploadFile(@RequestPart MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        long l = System.currentTimeMillis();
        filename = l + "-" + filename;
        String now = LocalDate.now().toString();
        String relDir = now + "/";
        String relPath = relDir + filename;
        dir = dir + "/" + relDir;
        File dirFileOnj = new File(dir);
        if (!dirFileOnj.exists()) {
            boolean b = dirFileOnj.mkdirs();
            if (!b) return null;
        }
        String fileAbsPath = dir+"/"+filename;
        File dest = new File(fileAbsPath);
        Files.copy(file.getInputStream(), dest.toPath());

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilename(filename);
        fileInfo.setRelPath(relPath);
        fileInfo.setIsDel(0);

        fileService.save(fileInfo);
        return fileInfo;
    }

    @Operation(summary = "下载图片")
    @GetMapping("/downloadFile/{id}")
    public String downloadFile(HttpServletResponse response, @PathVariable String id) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(id);
        FileInfo fileInfoInDB = fileService.getById(fileInfo);
        String relPath = fileInfoInDB.getRelPath();
        String filename = fileInfoInDB.getFilename();
        String absPath = dir + "/" + relPath;

        File file = new File(absPath);
        if (!file.exists()) {
            return "下载文件不存在";
        }

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" +  filename);

        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(file.toPath()));
            byte[] buff = new byte[1024];
            ServletOutputStream outputStream = response.getOutputStream();
            int i;
            while ((i = bufferedInputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, i);
                outputStream.flush();
            }
        }catch (Exception e) {
            log.error("下载失败： {}", e.getMessage());
            e.printStackTrace();
        }
        return "ok";
    }

}
