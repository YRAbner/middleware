package com.myitem.middleware.annotation;

import java.lang.annotation.*;

/**
 * @author: yr
 * @desc: 白名单自定义注解
 * @date: 2022/2/22 3:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface DoWhiteList {
    String key() default "";
    String returnJson() default "";
}
