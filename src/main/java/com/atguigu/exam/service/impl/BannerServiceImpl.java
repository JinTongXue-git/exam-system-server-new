package com.atguigu.exam.service.impl;

import com.atguigu.exam.entity.Banner;
import com.atguigu.exam.mapper.BannerMapper;
import com.atguigu.exam.service.BannerService;

import com.atguigu.exam.service.FileUploadService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 轮播图服务实现类
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Autowired
    FileUploadService fileUploadService;

    @Override
    public String uploadBannerImage(MultipartFile file) throws Exception {
        if (file.isEmpty()){
            throw new RuntimeException("上传的图片文件为空");
        }
        String contentType = file.getContentType();
        if ( ObjectUtils.isEmpty(contentType) || !contentType.startsWith("image")){
            throw new RuntimeException("上传的图片文件格式错误");
        }

        String imageUrl = fileUploadService.uploadBannerImage("banners", file);

        return imageUrl;
    }
}