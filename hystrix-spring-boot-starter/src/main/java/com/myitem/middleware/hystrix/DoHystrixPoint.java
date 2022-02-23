package com.myitem.middleware.hystrix;

import com.myitem.middleware.hystrix.annotation.DoHystrix;
import com.myitem.middleware.hystrix.value.IValveService;
import com.myitem.middleware.hystrix.value.impl.HystrixValveServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author: yr
 * @desc: 切点
 * @date: 2022/2/22 7:10
 */
@Aspect
@Component
public class DoHystrixPoint {
    @Pointcut("@annotation(com.myitem.middleware.hystrix.annotation.DoHystrix)")
    public void aopPoint(){}

    @Around("aopPoint() && @annotation(doHystrix)")
    public Object doRouter(ProceedingJoinPoint proceedingJoinPoint , DoHystrix doHystrix) throws NoSuchMethodException {
        IValveService iValveService = new HystrixValveServiceImpl();
        return iValveService.access(proceedingJoinPoint , getMethod(proceedingJoinPoint) , doHystrix , proceedingJoinPoint.getArgs());
    }

    public Method getMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return joinPoint.getTarget().getClass().getMethod(methodSignature.getName() , methodSignature.getParameterTypes());
    }
}
