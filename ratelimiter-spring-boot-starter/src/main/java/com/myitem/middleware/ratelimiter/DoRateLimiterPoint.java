package com.myitem.middleware.ratelimiter;

import com.myitem.middleware.ratelimiter.annotation.DoRateLimiter;
import com.myitem.middleware.ratelimiter.valve.IValveService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 8:49
 */
@Aspect
@Component
public class DoRateLimiterPoint {
    @Resource
    private IValveService iValveService;

    @Pointcut("@annotation(com.myitem.middleware.ratelimiter.annotation.DoRateLimiter)")
    public void aop(){}

    @Around("aop() && @annotation(doRateLimiter)")
    public Object access(ProceedingJoinPoint proceedingJoinPoint , DoRateLimiter doRateLimiter) throws Throwable {
        return iValveService.access(proceedingJoinPoint , getMethod(proceedingJoinPoint) ,
                doRateLimiter , proceedingJoinPoint.getArgs());
    }

    public Method getMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return joinPoint.getTarget().getClass().
                getMethod(methodSignature.getName() ,
                            methodSignature.getParameterTypes());
    }
}
