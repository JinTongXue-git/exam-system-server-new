package com.atguigu.exam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 项目：exam-system-server-new
 * 包名：com.atguigu.exam.config
 * 类名：AopConfig
 * 描述：AOP 核心开启注解
 * 作者：MechrevoUser1
 * 日期：2026/6/3 22:41
 * 用到的注解依赖：
 */

// ====================== AOP 核心开启注解 ======================
// proxyTargetClass = true：强制使用 CGLIB 代理，完美兼容 JDK21 + 你的所有 Controller/Service
// 作用：手动激活 AOP 功能，替代原来 starter 的自动配置
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class AopConfig {

}
