package com.myitem.middleware.ratelimiter.valve;

import com.myitem.middleware.ratelimiter.annotation.DoRateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 8:34
 */
public interface IValveService {

    Object access(ProceedingJoinPoint proceedingJoinPoint , Method method , DoRateLimiter doRateLimiter , Object[] args) throws Throwable;
}
