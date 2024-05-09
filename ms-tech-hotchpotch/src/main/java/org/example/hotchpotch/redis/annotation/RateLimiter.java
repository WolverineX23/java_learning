package org.example.hotchpotch.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口限制注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)     // 指定了被它所注解的注解的保留策略: 注解在运行时保留，因此可以通过反射机制读取。
public @interface RateLimiter {

    // key 的名称，用于 Redis 锁的键
    String[] keys() default {};


    // 令牌桶的容量, 默认300
    int capacity() default 300;

    // 生成令牌的速度, 默认每秒 100 个
    int rate() default 100;

    // 拒绝请求时的提示信息
    String showPromptMsg() default "服务器繁忙，请稍候再试！";

}
