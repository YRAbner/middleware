package com.myitem.middleware.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yr
 * @desc: 加载配置文件
 *         聚合 WhiteListProperties ：为 WhiteListAutoConfigure 提供配置数据
 * @date: 2022/2/22 4:17
 */
@Configuration
@ConditionalOnClass(WhiteListProperties.class)
@EnableConfigurationProperties(WhiteListProperties.class)
public class WhiteListAutoConfigure {
    @Bean("whiteListConfig")
    @ConditionalOnMissingBean
    public String whiteListConfig(WhiteListProperties whiteListProperties) {
        return whiteListProperties.getUsers();
    }
}
