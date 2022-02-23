package com.myitem.middleware.mybatis.boot.test;

import com.alibaba.fastjson.JSON;
import com.myitem.middleware.mybatis.boot.test.infrastructure.dao.IUserDao;
import com.myitem.middleware.mybatis.boot.test.infrastructure.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/23 11:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Resource
    private IUserDao userDao;

    @Test
    public void test_queryUserInfoById() {
        User user = userDao.queryUserInfoById(1L);
        logger.info("测试结果：{}", JSON.toJSONString(user));
    }

}

