package com.myitem.middleware.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: yr
 * @desc: 加载配置文件属性存储的类，将配置文件的属性进行存储，然后与 WhiteListAutoConfigure 同时加载为 bean 。
 * @date: 2022/2/22 4:07
 */
@ConfigurationProperties("bugstack.whitelist")
public class WhiteListProperties {
    private String users;

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}
