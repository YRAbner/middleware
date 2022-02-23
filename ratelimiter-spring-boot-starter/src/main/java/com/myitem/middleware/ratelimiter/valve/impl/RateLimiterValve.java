package com.myitem.middleware.ratelimiter.valve.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.myitem.middleware.ratelimiter.Constants;
import com.myitem.middleware.ratelimiter.annotation.DoRateLimiter;
import com.myitem.middleware.ratelimiter.valve.IValveService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 8:35
 */
@Service
public class RateLimiterValve implements IValveService {
    @Override
    public Object access(ProceedingJoinPoint proceedingJoinPoint, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable {
        //校验是否开启
        if (0 == doRateLimiter.permitsPreSecond()){
            return proceedingJoinPoint.proceed();
        }

        String clazzName = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = method.getName();

        String key = clazzName + "." + methodName;

        if (null == Constants.rateLimiterMap.get(key)){
            Constants.rateLimiterMap.put(key , RateLimiter.create(doRateLimiter.permitsPreSecond()));
        }
        RateLimiter rateLimiter = Constants.rateLimiterMap.get(key);
        if (rateLimiter.tryAcquire()){
            return proceedingJoinPoint.proceed();
        }

        return JSON.parseObject(doRateLimiter.returnJson() , method.getReturnType());
    }
}
