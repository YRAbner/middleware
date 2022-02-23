package com.myitem.middleware.mybatis.boot.test.interfaces;

import com.myitem.middleware.mybatis.boot.test.infrastructure.dao.IUserDao;
import com.myitem.middleware.mybatis.boot.test.infrastructure.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/23 11:22
 */
@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    //    @Resource
    private IUserDao userDao;

    @RequestMapping(path = "/api/queryUserInfoById", method = RequestMethod.GET)
    public User queryUserInfoById() {
        return userDao.queryUserInfoById(1L);
    }

}


