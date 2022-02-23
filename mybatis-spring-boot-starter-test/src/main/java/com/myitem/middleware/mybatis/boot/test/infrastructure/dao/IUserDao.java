package com.myitem.middleware.mybatis.boot.test.infrastructure.dao;

import com.myitem.middleware.mybatis.boot.test.infrastructure.po.User;
import org.springframework.stereotype.Repository;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/23 11:19
 */
public interface IUserDao {
    User queryUserInfoById(Long id);
}
