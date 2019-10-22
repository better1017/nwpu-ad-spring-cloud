package edu.nwpu.ad.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义注解，避免有些不需要响应
 * 加了此注解的都不需要响应
 * 可以标识在类上，也可以标识在方法上
 * 使用的Retention是在运行时
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
