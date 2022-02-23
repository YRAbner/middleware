package com.myitem.middleware.mybatis.boot.mybatis;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/23 10:22
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}
