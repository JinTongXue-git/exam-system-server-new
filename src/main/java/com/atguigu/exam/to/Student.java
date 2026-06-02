package com.atguigu.exam.to;

/**
 * 项目：exam-system-server-new
 * 包名：com.atguigu.exam.to
 * 类名：Student
 * 描述：用来当测试数据的--实体类--
 * 作者：MechrevoUser1
 * 日期：2026/6/2 15:41
 * 用到的注解依赖：
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String className;
}
