package com.atguigu.exam.service.impl;


import com.atguigu.exam.entity.Category;
import com.atguigu.exam.mapper.CategoryMapper;
import com.atguigu.exam.mapper.QuestionMapper;
import com.atguigu.exam.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 
     * 实现逻辑：
     * 1. 查询所有分类，按排序字段升序排列
     * 2. 统计每个分类下的题目数量
     * 3. 将题目数量填充到分类对象中
     * 
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
}