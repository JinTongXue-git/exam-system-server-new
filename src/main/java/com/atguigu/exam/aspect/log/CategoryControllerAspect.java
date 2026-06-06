package com.atguigu.exam.aspect.log;

import com.atguigu.exam.common.Result;
import com.atguigu.exam.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CategoryController 日志切面
 * 记录分类管理相关接口的调用日志和执行耗时
 */
@Slf4j
@Aspect
@Component
public class CategoryControllerAspect {

    /**
     * 获取分类列表（包含题目数量）
     */
    @Around("execution(* com.atguigu.exam.controller.CategoryController.getCategories(..))")
    public Object getCategories(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("================================================");
        String methodName = joinPoint.getSignature().getName();
        log.info("执行方法：{}", methodName);
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        log.info("方法执行完成，耗时：{}ms", (System.currentTimeMillis() - start));

        @SuppressWarnings("unchecked")
        Result<List<Category>> categoryResult = (Result<List<Category>>) result;
        List<Category> categories = categoryResult.getData();
        log.info("获取到 {} 条分类数据", categories != null ? categories.size() : 0);
        log.info("================================================");
        return result;
    }

    /**
     * 获取分类树形结构
     */
    @Around("execution(* com.atguigu.exam.controller.CategoryController.getCategoryTree(..))")
    public Object getCategoryTree(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("================================================");
        String methodName = joinPoint.getSignature().getName();
        log.info("执行方法：{}", methodName);
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        log.info("方法执行完成，耗时：{}ms", (System.currentTimeMillis() - start));

        @SuppressWarnings("unchecked")
        Result<List<Category>> treeResult = (Result<List<Category>>) result;
        List<Category> treeData = treeResult.getData();
        log.info("获取到 {} 个一级分类（树形结构）", treeData != null ? treeData.size() : 0);
        log.info("================================================");
        return result;
    }

    /**
     * 添加分类
     */
    @Around("execution(* com.atguigu.exam.controller.CategoryController.addCategory(..))")
    public Object addCategory(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Category category = args.length == 1 ? (Category) args[0] : null;
        log.info("添加分类，参数：{}", category);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        log.info("添加分类完成，耗时：{}ms", (System.currentTimeMillis() - start));
        return result;
    }

    /**
     * 更新分类
     */
    @Around("execution(* com.atguigu.exam.controller.CategoryController.updateCategory(..))")
    public Object updateCategory(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Category category = args.length == 1 ? (Category) args[0] : null;
        log.info("更新分类，参数：{}", category);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        log.info("更新分类完成，耗时：{}ms", (System.currentTimeMillis() - start));
        return result;
    }

    /**
     * 删除分类
     */
    @Around("execution(* com.atguigu.exam.controller.CategoryController.deleteCategory(..))")
    public Object deleteCategory(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long id = args.length == 1 ? (Long) args[0] : null;
        log.info("删除分类，ID：{}", id);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        log.info("删除分类完成，ID：{}，耗时：{}ms", id, (System.currentTimeMillis() - start));
        return result;
    }
}