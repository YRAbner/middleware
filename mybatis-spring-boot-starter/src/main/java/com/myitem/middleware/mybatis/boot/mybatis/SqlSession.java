package com.myitem.middleware.mybatis.boot.mybatis;

import java.util.List;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/23 10:22
 */
public interface SqlSession {
    //查询一个
    <T>T selectOne(String statement);
    //根据条件查询一个
    <T>T selectOne(String statement , Object parameter);
    //查列表
    <T> List<T> selectList(String statement);
    <T>List<T> selectList(String statement , Object parameter);
    //关闭连接
    void close();
}
