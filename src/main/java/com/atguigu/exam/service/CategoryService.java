package com.atguigu.exam.service;

import com.atguigu.exam.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {


    /**
     * 获取分类列表（包含题目数量）
     * @return 所有分类列表
     */
    List<Category> findCategoryList();

    /**
     * 获取分类树形结构
     * */
    List<Category> findCategoryTreeList();


    /**
     * 添加分类
     * @param category 分类对象
     */
    void saveCategory(Category category);


    /**
     * 更新分类
     * @param category 分类对象
     */
    void updateCategory(Category category);

    /**
     * 删除分类
     * @param id 分类ID
     */
    void removeCategory(Long id);
}