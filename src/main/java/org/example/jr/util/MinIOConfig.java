package org.example.jr.util;

/**
 * @author xingxing
 * @date 2024/7/11 16:33:07
 * @apiNote
 */
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.example.jr.entity.MinIO;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinIO.class)
public class MinIOConfig {

    @Bean
    public MinioClient minioClient(MinIO minIO) throws InvalidPortException, InvalidEndpointException {
        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minIO.getEndpoint())
                .credentials(minIO.getAccessKey(), minIO.getSecretKey())
                .build();
        return minioClient;
    }
}