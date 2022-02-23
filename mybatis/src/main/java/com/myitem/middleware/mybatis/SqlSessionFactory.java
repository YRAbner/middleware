package com.myitem.middleware.mybatis;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 22:56
 */
public interface SqlSessionFactory{
    SqlSession openSession();
}
