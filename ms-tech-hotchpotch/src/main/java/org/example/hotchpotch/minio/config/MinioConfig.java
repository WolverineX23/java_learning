package org.example.hotchpotch.minio.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    // MinIO 连接地址
    private String endpoint;

    // accessKey 或 账号
    private String accessKey;

    // secretKey 或 密码
    private String secretKey;

    // 桶名称
    private String bucketName;

    // 地址过期时间，默认是秒
    private int expire = 86400;

    /* dep
    private String accessKey;

    private String secretKey;

    private String url;

    private String bucketName;

    @Bean
    public MinioClient minioClient() {

        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
     */
}
