package com.myitem.middleware.mybatis.test.dao;

import com.myitem.middleware.mybatis.test.po.User;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 22:58
 */
public interface IUserDao {
    User queryUserInfoById(Long id);
}
