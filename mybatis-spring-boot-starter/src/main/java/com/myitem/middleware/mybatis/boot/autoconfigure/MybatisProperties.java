package com.myitem.middleware.mybatis.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @author: yr
 * @desc:
 * @ConfigurationProperties，用于创建指定前缀( prefix = MybatisProperties.MYBATIS_PREFIX )的自定义配置信息，
 * 这样就在 yml 或者 properties 中读取到我们自己设定的配置信息。
 * 配置信息：driver、url、username、password、mapperLocations、baseDaoPackage，
 * 这些只要在 SpringBoot 中使用过 MyBatis 就比较熟悉了。
 * @date: 2022/2/23 10:22
 */
@ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
public class MybatisProperties {
    public static final String MYBATIS_PREFIX = "mybatis.datasource";

    private String driver;              // com.mysql.jdbc.Driver
    private String url;                 // jdbc:mysql://127.0.0.1:3306/bugstack?useUnicode=true
    private String username;            // root
    private String password;            // 123456
    private String mapperLocations;    // classpath*:mapper/*.xml
    private String baseDaoPackage;      // cn.bugstack.middleware.test.infrastructure.dao

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getBaseDaoPackage() {
        return baseDaoPackage;
    }

    public void setBaseDaoPackage(String baseDaoPackage) {
        this.baseDaoPackage = baseDaoPackage;
    }
}
