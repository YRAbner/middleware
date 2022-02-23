package com.myitem.middleware.methodext;

import com.alibaba.fastjson.JSON;
import com.myitem.middleware.methodext.annotation.DoMethodExt;
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
 * @desc:
 * @date: 2022/2/22 10:18
 */
@Aspect
@Component
public class DoMethodExtPoint {
    @Pointcut("@annotation(com.myitem.middleware.methodext.annotation.DoMethodExt)")
    public void aop(){}

    @Around("aop()")
    public Object doRouter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Method method = getMethod(proceedingJoinPoint);
        DoMethodExt doMethodExt = method.getAnnotation(DoMethodExt.class);

        String methodName = doMethodExt.method();

        Method methodExt = getClass(proceedingJoinPoint).getMethod(methodName , method.getParameterTypes());
        Class<?> classType = methodExt.getReturnType();

        if (!classType.getName().equals("boolean")){
            throw new RuntimeException("annotation @DoMethodExt set method：" + methodName + " returnType is not boolean");
        }

        boolean invoke = (boolean) methodExt.invoke(proceedingJoinPoint.getThis() , proceedingJoinPoint.getArgs());

        return invoke?proceedingJoinPoint.proceed(): JSON.parseObject(doMethodExt.returnJson() , method.getReturnType());
    }

    public Method getMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return joinPoint.getTarget().getClass().getMethod(methodSignature.getName() , methodSignature.getParameterTypes());
    }

    public Class<? extends Object> getClass(JoinPoint joinPoint){
        return joinPoint.getTarget().getClass();
    }
}
