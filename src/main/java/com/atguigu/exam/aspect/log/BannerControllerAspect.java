package com.atguigu.exam.aspect.log;

import com.atguigu.exam.common.Result;
import com.atguigu.exam.entity.Banner;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目：exam-system-server-new
 * 包名：com.atguigu.exam.aspect
 * 类名：BannerControllerAspect
 * 描述：BannerController的日志切面
 * 作者：MechrevoUser1
 * 日期：2026/6/3 22:39
 * 用到的注解依赖：
 */
@Slf4j
@Aspect
@Component
public class BannerControllerAspect {

    @Pointcut()
    public void logPointcut(){}

    /**
     * 获取所有启用的轮播图（前台首页使用）
     */
    @Around("execution(* com.atguigu.exam.controller.BannerController.getActiveBanners(..) )")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 方法执行前
        String methodName = joinPoint.getSignature().getName();
        log.info("执行方法：{}", methodName);
        long start = System.currentTimeMillis();

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 方法执行后
        log.info("方法执行完成，耗时：{}ms", (System.currentTimeMillis() - start));
        /*
         * ============================================================
         * 泛型转型说明
         * ============================================================
         * joinPoint.proceed() 返回的是 Object 类型，
         * 但实际运行时 getActiveBanners() 返回的是 Result<List<Banner>>。
         *
         * 由于 Java 泛型擦除（Type Erasure），编译后 JVM 只知道它是 Result，
         * 无法在运行时验证内部的 <List<Banner>>，
         * 所以这里用 @SuppressWarnings("unchecked") 抑制编译器的"未检查转型"警告。
         *
         * 这个抑制是安全的，因为我们明确知道切点匹配的方法是 getActiveBanners()，
         * 它的返回值就是 Result<List<Banner>>。
         * ============================================================
         */
        @SuppressWarnings("unchecked")
        Result<List<Banner>> bannerResult = (Result<List<Banner>>) result;
        List<Banner> banners = bannerResult.getData();
        log.info("获取到 {} 条启用的轮播图", banners != null ? banners.size() : 0);
        return result;
    }

    /**
     * 获取--"所有"--轮播图（管理后台使用）
     * */
    @Around("execution(* com.atguigu.exam.controller.BannerController.getAllBanners(..) )")
    public Object getAllBanners(ProceedingJoinPoint joinPoint) throws Throwable {
        // 方法执行前
        String methodName = joinPoint.getSignature().getName();
        log.info("执行方法：{}", methodName);
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();
        List<Banner> banners = null;
        if (result instanceof Result ){
            banners = ((Result<List<Banner>>) result).getData();
        }
        log.info("获取到 {} 条轮播图 , 耗时: {}ms", banners != null ? banners.size() : 0, System.currentTimeMillis() - start);
        return result;

    }



    @Around("execution(* com.atguigu.exam.controller.BannerController.getBannerById(..))")
    public Object getBannerById(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        Long id =  args.length == 1 ? (Long)args[0] : null;
        log.info("获取轮播图详情，ID: {}------", id);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("获取轮播图详情，ID: {},耗时: {}ms------", id, System.currentTimeMillis() - start);
        return result;

    }



//    删除轮播图
    @Around("execution(* com.atguigu.exam.controller.BannerController.deleteBanner(..))")
    public Object deleteBanner(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        Long id = args.length == 1 ? (Long) args[0] : null;

        log.info("删除轮播图，ID:{}", id);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("删除轮播图，ID: {},{}耗时: {}ms",
                id,  "删除成功" ,System.currentTimeMillis() - start);
        return result;
    }






    /**切换轮播图状态*/
    @Around("execution(* com.atguigu.exam.controller.BannerController.toggleBannerStatus(..))")
    public Object toggleBannerStatus(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        Long id = args.length > 0 ? (Long) args[0] : null;
        Boolean isActive = args.length > 1 ? (Boolean) args[1] : null;

        log.info("轮播图状态切换，ID: {}, 要修改的状态{}", id,  isActive);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("轮播图状态切换，ID: {},{}耗时: {}ms",
                id,  "修改banners表的is_active(启动状态)为成功" ,System.currentTimeMillis() - start);
        return result;
    }

}
