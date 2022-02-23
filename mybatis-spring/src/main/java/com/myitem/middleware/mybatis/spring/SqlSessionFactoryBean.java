package com.myitem.middleware.mybatis.spring;

import com.myitem.middleware.mybatis.Resources;
import com.myitem.middleware.mybatis.SqlSessionFactory;
import com.myitem.middleware.mybatis.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Reader;

/**
 * @author: yr
 * @desc:
 *      这类本身比较简单，主要实现了 FactoryBean, InitializingBean 用于帮我们处理 mybaits 核心流程类的加载处理。
 *      实现 InitializingBean 主要用于加载 mybatis 相关内容；解析 xml、构造 SqlSession、链接数据库等。
 *      FactoryBean，这个类我们介绍过，主要三个方法；getObject()、getObjectType()、isSingleton()
 * @date: 2022/2/23 9:11
 */
public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean {
    private String resource;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        try (Reader reader = Resources.getResourceAsReader(resource)) {
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SqlSessionFactory getObject() throws Exception {
        return sqlSessionFactory;
    }

    @Override
    public Class<?> getObjectType() {
        return sqlSessionFactory.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
