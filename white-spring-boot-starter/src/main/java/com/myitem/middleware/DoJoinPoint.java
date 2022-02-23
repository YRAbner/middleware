package com.myitem.middleware;

import com.alibaba.fastjson.JSON;
import com.myitem.middleware.annotation.DoWhiteList;
import com.myitem.middleware.config.WhiteListAutoConfigure;
import com.myitem.middleware.config.WhiteListProperties;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author: yr
 * @desc: 核心：负责拦截与逻辑
 * @date: 2022/2/22 4:27
 */
@Aspect
@Component
public class DoJoinPoint {
    private Logger logger = LoggerFactory.getLogger(DoJoinPoint.class);

    //名称注入 whiteListConfig ：因为加载bean时，加载的名称为 whiteListConfig
    @Resource
    private String whiteListConfig;

    /**
     *     定义切点。
     *     在 Pointcut 中提供了很多的切点寻找方式，有指定方法名称的、有范围筛选表达式的，也有我们现在通过自定义注解方式的。
     *     一般在中间件开发中，自定义注解方式使用的比较多，因为它可以更加灵活的运用到各个业务系统中。
     */
    @Pointcut("@annotation(com.myitem.middleware.annotation.DoWhiteList)")
    public void aopPoint(){
    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取方法属性
        Method method = getMethod(proceedingJoinPoint);
        DoWhiteList doWhiteList = method.getAnnotation(DoWhiteList.class);

        //获取字段值
        String keyValue = getFiledValue(doWhiteList.key() , proceedingJoinPoint.getArgs());
        logger.info("middleware whitelist handle method:{} value:{}" , method.getName() , keyValue);

        if (null == keyValue || "".equals(keyValue)){
            return proceedingJoinPoint.proceed();
        }

        String[] split = whiteListConfig.split(",");

        //白名单过滤
        for (String str:split){
            if (keyValue.equals(str)){
                return proceedingJoinPoint.proceed();
            }
        }
        return returnObject(doWhiteList,method);
    }

    //获取切点方法的属性
    private Method getMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return joinPoint.getTarget().getClass().getMethod(methodSignature.getName() , methodSignature.getParameterTypes());
    }

    //获取方法字段值
    private String getFiledValue(String filed , Object[] args){
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (null == filedValue || "".equals(filedValue)){
                    filedValue = BeanUtils.getProperty(arg , filed);
                }else {
                    break;
                }
            }catch (Exception e){
                if (args.length == 1){
                    return args[0].toString();
                }
            }
        }
        return filedValue;
    }
    //拦截
    public Object returnObject(DoWhiteList doWhiteList , Method method) throws InstantiationException, IllegalAccessException {
        Class<?> returnType = method.getReturnType();
        String returnJson = doWhiteList.returnJson();
        if ("".equals(returnJson)){
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson,returnType);
    }

}
