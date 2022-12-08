package fun.verda.SceneExample.module.oss.component;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MinioClientComponent {

    @Value("${s3.endpoint}")
    private String endpoint;

    @Value("${s3.accessKey}")
    private String accessKey;

    @Value("${s3.secretKey}")
    private String secretKey;


    @Bean
    public MinioClient mc() {
        return MinioClient.builder()
                .endpoint(endpoint).credentials(accessKey, secretKey).build();
    }
}
