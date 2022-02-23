package com.myitem.middleware.mybatis;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 22:56
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
