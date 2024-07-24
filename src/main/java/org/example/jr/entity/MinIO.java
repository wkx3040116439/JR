package org.example.jr.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "minio")
public class MinIO {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}