package com.myitem.middleware.mybatis.test;

import com.alibaba.fastjson.JSON;
import com.myitem.middleware.mybatis.Resources;
import com.myitem.middleware.mybatis.SqlSession;
import com.myitem.middleware.mybatis.SqlSessionFactory;
import com.myitem.middleware.mybatis.SqlSessionFactoryBuilder;
import com.myitem.middleware.mybatis.test.po.User;
import org.junit.Test;

import java.io.Reader;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 22:57
 */
public class ApiTest {
    @Test
    public void test_queryUserInfoById() {
        String resource = "mybatis-config-datasource.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlMapper.openSession();
            try {
                User user = session.selectOne("com.myitem.middleware.mybatis.test.dao.IUserDao.queryUserInfoById", 1L);
                System.out.println(JSON.toJSONString(user));
            } finally {
                session.close();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
