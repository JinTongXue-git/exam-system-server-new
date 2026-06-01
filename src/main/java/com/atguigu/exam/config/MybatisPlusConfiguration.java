package com.atguigu.exam.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 项目：exam-system-server-new
 * 包名：com.atguigu.exam.config
 * 类名：MybatisPlusConfiguration
 * 描述：TODO
 * 作者：MechrevoUser1
 * 日期：2026/6/1 21:50
 * 用到的注解依赖：
 */
@MapperScan("com.atguigu.exam.mapper")
@Configuration
public class MybatisPlusConfiguration {
}
