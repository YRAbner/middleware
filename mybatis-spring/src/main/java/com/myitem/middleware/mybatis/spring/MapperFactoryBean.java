package com.myitem.middleware.mybatis.spring;

import com.myitem.middleware.mybatis.SqlSession;
import com.myitem.middleware.mybatis.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author: yr
 * @desc: 代理
 * 1、这个类就非常核心了，因为你所有的 DAO 接口类，实际就是它。他这里帮你执行你对 SQL 的所有操作的分发处理。
 * 2、为了更加简化清晰，目前这里只实现了查询部分，在 mybatis-spring 源码中分别对select、update、insert、delete、其他等做了操作。
 * 3、T getObject()，中是一个 Java 代理类的实现，这个代理类对象会被挂到你的注入中。真正调用方法内容时会执行到代理类的实现部分，
 *      也就是“你被代理了，执行SQL操作！”
 * 4、InvocationHandler，代理类的实现部分非常简单，主要开启SqlSession，
 *      并通过固定的key：com.myitem.middleware.mybatis.spring.test.dao.IUserDao.queryUserInfoById执行SQL操作；
 * 5、最终返回了执行结果，关于查询到结果信息会反射操作成对象类，这里就是我们实现的 ORM 中间件负责的事情了。
 * @date: 2022/2/23 9:11
 */
public class MapperFactoryBean<T> implements FactoryBean<T> {
    private Logger logger = LoggerFactory.getLogger(MapperFactoryBean.class);

    private Class<T> mapperInterface;
    private SqlSessionFactory sqlSessionFactory;

    public MapperFactoryBean(Class<T> mapperInterface, SqlSessionFactory sqlSessionFactory) {
        this.mapperInterface = mapperInterface;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public T getObject() throws Exception {
        InvocationHandler handler = (proxy, method, args) -> {
            logger.info("你被代理了，执行SQL操作！{}", method.getName());
            try {
                SqlSession session = sqlSessionFactory.openSession();
                try {
                    return session.selectOne(mapperInterface.getName() + "." + method.getName(), args[0]);
                } finally {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return method.getReturnType().newInstance();
        };
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{mapperInterface}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
