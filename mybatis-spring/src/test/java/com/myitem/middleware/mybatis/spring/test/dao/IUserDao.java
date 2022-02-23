package com.myitem.middleware.mybatis.spring.test.dao;

import com.myitem.middleware.mybatis.spring.test.po.User;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/23 9:11
 */
public interface IUserDao {
    User queryUserInfoById(Long id);
}
