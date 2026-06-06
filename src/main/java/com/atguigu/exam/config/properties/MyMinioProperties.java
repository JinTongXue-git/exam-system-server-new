package com.atguigu.exam.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目：exam-system-server-new
 * 包名：com.atguigu.exam.config.properties
 * 类名：MinioProperties
 * 描述：minio的配置类，接收minio的参数【endPoint，accesskey，secretkey，bucketName】
 * 作者：MechrevoUser1
 * 日期：2026/6/5 22:30
 * 用到的注解依赖：
 */
@Data
@ConfigurationProperties(prefix = "minio")
@Component
public class MyMinioProperties {
    private String endPoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
