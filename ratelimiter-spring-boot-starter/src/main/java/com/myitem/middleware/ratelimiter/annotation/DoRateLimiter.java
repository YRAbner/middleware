package com.myitem.middleware.ratelimiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 8:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoRateLimiter {
    //限流允许量
    int permitsPreSecond() default 0;
    //失败返回
    String returnJson() default "";
}
