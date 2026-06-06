package com.atguigu.exam.service;

import com.atguigu.exam.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

/**
 * 轮播图服务接口
 */
public interface BannerService extends IService<Banner> {

    /**
     * 上传单轮播图图片到服务器（minio）
     * @param file 上传的文件
     * @return 图片URL地址
     * @throws Exception 如果上传失败
     */
    public String uploadBannerImage(MultipartFile file) throws Exception;
}