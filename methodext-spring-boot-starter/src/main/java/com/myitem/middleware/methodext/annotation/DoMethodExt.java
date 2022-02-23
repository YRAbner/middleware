package com.myitem.middleware.methodext.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 10:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoMethodExt {

    String method() default "";

    String returnJson() default "";
}
