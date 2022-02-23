package com.myitem.middleware.mybatis.boot.mybatis;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/23 10:22
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration.connection, configuration.mapperElement);
    }
}
