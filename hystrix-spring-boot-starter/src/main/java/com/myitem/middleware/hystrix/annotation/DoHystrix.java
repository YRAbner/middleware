package com.myitem.middleware.hystrix.annotation;

import java.lang.annotation.*;

/**
 * @author: yr
 * @desc: 自定义hystrix注解
 * @date: 2022/2/22 7:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoHystrix {
    String returnJson() default "";
    int timeoutValue() default 0;
}
