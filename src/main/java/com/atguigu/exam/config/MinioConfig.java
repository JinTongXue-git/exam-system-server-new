package com.atguigu.exam.config;

import org.springframework.context.annotation.Configuration;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
/**
 * 项目：exam-system-server-new
 * 包名：com.atguigu.exam.config
 * 类名：MinioConfig
 * 描述：TODO
 * 作者：刘老爷
 * 日期：2026/6/1 21:46
 * 用到的注解依赖：
 */


@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}