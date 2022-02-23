package com.myitem.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: yr
 * @desc: 启动类
 * @date: 2022/2/22 5:23
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.myitem.middleware.*"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class , args);
    }
}
