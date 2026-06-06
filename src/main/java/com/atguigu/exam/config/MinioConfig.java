package com.atguigu.exam.config;

import com.atguigu.exam.config.properties.MyMinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

    @Autowired
    private MyMinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {


        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioProperties.getEndPoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        //初始化一个桶，用于存储轮播图图片文件
        try {
            boolean bool = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .build());

            if (bool == false) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .build());

                String policy = """
                    {
                       "Statement" : [ {
                         "Action" : "s3:GetObject",
                         "Effect" : "Allow",
                         "Principal" : "*",
                         "Resource" : "arn:aws:s3:::%s/*"
                       } ],
                       "Version" : "2012-10-17"
                    }
                    """.formatted(minioProperties.getBucketName());
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .config(policy)
                        .build()
                );
            }else {
                System.out.println(minioProperties.getBucketName()+"桶已存在------");
            }
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        return minioClient;
    }
}