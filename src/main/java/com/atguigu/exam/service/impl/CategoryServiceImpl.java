package com.atguigu.exam.service.impl;


import com.atguigu.exam.entity.Category;
import com.atguigu.exam.entity.Question;
import com.atguigu.exam.mapper.CategoryMapper;
import com.atguigu.exam.mapper.QuestionMapper;
import com.atguigu.exam.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    QuestionMapper questionMapper;

    /**
     * 获取分类列表（包含每个分类下的题目数量）
     * <p>
     * 实现逻辑：
     * 1. 查询所有分类，按排序字段升序排列
     * 2. 统计每个分类下的题目数量
     * 3. 将题目数量填充到分类对象中
     * <p>
     * 时间复杂度：O(n)
     * - 查询分类：O(n)
     * - 统计题目数量：O(m)
     * - 构建 Map：O(m)
     * - 填充数量：O(n)
     * - 总体：O(n + m)，其中 n 为分类数量，m 为题目数量
     *
     * @return 分类列表，每个分类包含题目数量
     */
    @Override
    public List<Category> findCategoryList() {
        // 1. 查询所有分类，按排序字段升序排列
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        List<Category> categoryList = list(queryWrapper);

        // 2. 统计每个分类下的题目数量
        // 查询结果示例：[{category_id: 14, count: 5}, {category_id: 15, count: 3}]
        List<Map<String, Long>> questionCountList = questionMapper.selectCategoryQuestionCount();

        // 3. 将题目数量列表转换为 Map，方便快速查找
        // 转换后：{14: 5, 15: 3, ...}
        Map<Long, Long> categoryQuestionCountMap = questionCountList.stream()
                .collect(Collectors.toMap(
                        countItem -> countItem.get("category_id"),
                        countItem -> countItem.get("count")
                ));

        // 4. 将题目数量填充到对应的分类对象中
        for (Category category : categoryList) {
            Long categoryId = category.getId();
            // 如果该分类没有题目，则设置为 0
            Long questionCount = categoryQuestionCountMap.getOrDefault(categoryId, 0L);
            category.setCount(questionCount);
        }

        return categoryList;
    }

    @Override
    public List<Category> findCategoryTreeList() {

        // 1. 查询所有分类，按排序字段升序排列
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        List<Category> categoryList = list(queryWrapper);

        // 2. 统计每个分类下的题目数量
        // 查询结果示例：[{category_id: 14, count: 5}, {category_id: 15, count: 3}]
        List<Map<String, Long>> questionCountList = questionMapper.selectCategoryQuestionCount();

        // 3. 将题目数量列表转换为 Map，方便快速查找
        // 转换后：{14: 5, 15: 3, ...}
        Map<Long, Long> categoryQuestionCountMap = questionCountList.stream()
                .collect(Collectors.toMap(
                        countItem -> countItem.get("category_id"),
                        countItem -> countItem.get("count")
                ));

        // 4. 将题目数量填充到对应的分类对象中
        for (Category category : categoryList) {
            Long categoryId = category.getId();
            // 如果该分类没有题目，则设置为 0
            Long questionCount = categoryQuestionCountMap.getOrDefault(categoryId, 0L);
            category.setCount(questionCount);
        }



         /*5. 将分类列表按 parent_id 分组，转换成 Map 结构
         转换前：List<Category> - 平铺的所有分类数据
         [
             {id:13, name:"选择题", parentId:0},    ← 一级
           {id:14, name:"判断题", parentId:0},    ← 一级
           {id:15, name:"简答题", parentId:0},    ← 一级
           {id:16, name:"Java基础", parentId:13},  ← 二级（过滤掉）
           {id:17, name:"Java进阶", parentId:13},  ← 二级（过滤掉）
           {id:21, name:"Java语法", parentId:14},  ← 二级（过滤掉）
           ...
         ]*/
        /*
         转换后：Map<Long, List<Category>> - 按 parentId 分组的树形结构
         {
           0:  [{id:13, name:"选择题", parentId:0}, {id:14, name:"判断题", parentId:0}, {id:15, name:"简答题", parentId:0}],
           13: [{id:16, name:"Java基础", parentId:13}, {id:17, name:"Java进阶", parentId:13}, ...],
           14: [{id:21, name:"Java语法", parentId:14}, {id:22, name:"编程规范", parentId:14}, ...],
           15: [{id:24, name:"系统设计", parentId:15}, {id:25, name:"性能优化", parentId:15}, ...]
         }
        */
        Map<Long, List<Category>> childrenByParentId = categoryList.stream().collect(Collectors.groupingBy(Category::getParentId));

        // 转换前：categoryList 包含所有分类（一级 + 二级）
        // 转换后：parentCategoryList 只包含一级分类
        // [
        //   {id:13, name:"选择题", parentId:0},
        //   {id:14, name:"判断题", parentId:0},
        //   {id:15, name:"简答题", parentId:0}
        // ]
        //6. 筛选分类信息 （要1级分类  parent_id == 0）
        List<Category> rootCategories = categoryList.stream()
                .filter(category -> category.getParentId() == 0)
                .toList();


        //7.给一级分类循环，获取子分类，并且计算count（父分类的count+所有子分类的count）
        for (Category root : rootCategories) {
            // 获取当前根分类的所有子分类
            List<Category> children = childrenByParentId.getOrDefault(root.getId(), new ArrayList<>());
            root.setChildren(children);

            // 计算父分类的题目数量（所有子分类的题目数量总和）
            root.setCount(children.stream().collect(Collectors.summingLong(Category::getCount)));
        }

        return rootCategories;
    }

    @Override
    public void saveCategory(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getParentId, category.getParentId());
        wrapper.eq(Category::getName, category.getName());

        if (count(wrapper) > 0) {
            throw new RuntimeException(String.format("%s分类名称已存在%s", category.getParentId(), category.getName()));
        }
        save(category);
    }

    @Override
    public void updateCategory(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getParentId, category.getParentId());
        wrapper.eq(Category::getName, category.getName());
        wrapper.ne(Category::getId, category.getId());

        if (count(wrapper) > 0) {
            throw new RuntimeException(String.format("更新失败： %s分类名称已存在%s", category.getParentId(), category.getName()));
        }

        updateById(category);
    }

    @Override
    public void removeCategory(Long id) {
        Category category = getById(id);
        if (category == null) {
            log.info("分类不存在: {}", id);
            throw new RuntimeException("分类不存在");
        }

//        1.判断是否是一级分类，一级分类不能删除
        if (category.getParentId() == 0) {
            throw new RuntimeException("一级分类不能删除");
        }

        // 2.判断是否有子分类，有子分类不能删除
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getCategoryId, id);

        Long questionCount = questionMapper.selectCount(wrapper);

        if (questionCount > 0) {
            throw new RuntimeException(String.format("该分类下有%s个关联题目，不能删除", questionCount));
        }

        removeById(id);


    }
}