package com.myitem.middleware.mybatis.boot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/23 11:13
 */
@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.myitem.middleware.mybatis")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class , args);
    }
}
