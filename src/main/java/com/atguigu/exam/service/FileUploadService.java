package com.atguigu.exam.service;


import com.atguigu.exam.common.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传服务
 * 支持MinIO和本地文件存储两种方式
 */

public interface FileUploadService {

    /**
     *  --待优化--
     * 上传文件到指定文件夹（minio）
     * @param folder 文件夹名称
     * @param file 上传的文件
     * @return 文件URL地址
     * @throws Exception 如果上传失败
     */
    public String uploadBannerImage(String folder ,  MultipartFile file) throws Exception;

} 