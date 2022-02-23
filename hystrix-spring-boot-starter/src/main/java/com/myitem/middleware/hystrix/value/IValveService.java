package com.myitem.middleware.hystrix.value;

import com.myitem.middleware.hystrix.annotation.DoHystrix;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author: yr
 * @desc: 熔断接口
 * @date: 2022/2/22 7:11
 */
public interface IValveService {

    public Object access(ProceedingJoinPoint proceedingJoinPoint , Method method , DoHystrix doHystrix , Object[] args);

    public Object getFallback();
}
