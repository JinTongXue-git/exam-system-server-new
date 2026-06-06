package com.atguigu.exam.service.impl;

import com.atguigu.exam.config.properties.MyMinioProperties;
import com.atguigu.exam.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * projectName: com.atguigu.exam.service.impl
 *
 * @author: 赵伟风
 * description:
 */
@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MyMinioProperties minioProperties;

    /**
     * 上传图片文件到MinIO
     * 将文件上传到指定的bucket中，并返回可直接访问的URL
     * 注意：上传时需要身份验证，但访问时不需要，因此直接拼接公开访问URL返回
     * 
     * @param folder 目标文件夹路径（如：banners、avatars等），不是bucket名
     * @param file 要上传的图片文件
     * @return 图片的公开访问URL，格式为：endpoint/bucketName/folder/date/uuid_originalFilename
     */
    @Override
    public String uploadBannerImage(String folder ,  MultipartFile file) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-" , "");
        String time = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        //uuid保证文件名唯一，加下划线分隔可以还原原始文件名，不用数据库维护
        //folder文件夹名，time时间，uuid文件名，file.getOriginalFilename()文件原始名
        String fileName = folder + "/" + time + "/" + uuid + "_" + file.getOriginalFilename();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .contentType(file.getContentType())
                        .object(fileName) // 存储在MinIO的文件名
                        .stream(file.getInputStream() , file.getSize() , -1)
                        .build()
        );
        // 域名 + 桶名 + 文件名
        String imageUrl = minioProperties.getEndPoint() + "/" + minioProperties.getBucketName() + "/"+ fileName;
        log.info("------上传轮播图图片成功，图片回显URL：{}" , imageUrl);
        return imageUrl;
    }
}