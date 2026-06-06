package com.atguigu.exam.service;

import com.atguigu.exam.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {


    /**
     * 查询所有分类，包括子分类
     * @return 所有分类列表
     */
    List<Category> findCategoryList();
}