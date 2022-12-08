package fun.verda.SceneExample.module.oss.service.impl;

import fun.verda.SceneExample.module.oss.service.OssService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Resource
    private MinioClient mc;

    @Value("${s3.bucket}")
    private String bucket;


    public void fileUpload(String object, String filename) throws IOException {
        fileUpload(bucket, object, filename);
    }

    public void fileUpload(String bucket, String object, String filename) throws IOException {
        UploadObjectArgs fileArg = UploadObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .filename(filename).build();
        try {
            mc.uploadObject(fileArg);
        }catch (MinioException e) {
            log.error("Error occurred: {}", e.getMessage());
            log.error("HTTP trace: {}", e.httpTrace());
        }

        catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage());
        }
    }

    public String getObjectUrl() {
        HashMap<String, String> reqParams = new HashMap<>();
            reqParams.put("response-content-type", "video/mp4");

        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucket)
                .object("bg.mp4")
                .expiry(2, TimeUnit.MINUTES)
                .extraQueryParams(reqParams)
                .build();

        try {
            return mc.getPresignedObjectUrl(args);
        }catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage());
        }
        return null;
    }

}
