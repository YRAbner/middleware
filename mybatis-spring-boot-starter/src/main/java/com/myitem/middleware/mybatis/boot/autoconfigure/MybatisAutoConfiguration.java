package com.myitem.middleware.mybatis.boot.autoconfigure;

import com.myitem.middleware.mybatis.boot.mybatis.SqlSessionFactory;
import com.myitem.middleware.mybatis.boot.mybatis.SqlSessionFactoryBuilder;
import com.myitem.middleware.mybatis.boot.spring.MapperFactoryBean;
import com.myitem.middleware.mybatis.boot.spring.MapperScannerConfigurer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author: yr
 * @desc: 主要包括了实例化两个 Bean(connection、sqlSessionFactory) 和处理自动扫描注册 Bean 信息到 Spring 容器中。
 * @EnableConfigurationProperties(MybatisProperties.class)，用于引入配置信息，如果你不引入是不能使用配置信息的。
 * Connection connection(MybatisProperties mybatisProperties)，实例化数据库连接，
 *      mybatisProperties 是这个方法的入参，这里也可以通过属性注入方法体里使用的方式进行处理。
 * 另外，这里返回的数据库连接方式，是我们从类 SqlSessionFactoryBuilder 提取出来的，
 *      这样的方式也非常符合官网中 mybaits SpringBoot Starter 的处理方式，因为这样可以更加方便的增加其它的数据库连接池。
 *      SqlSessionFactory sqlSessionFactory(Connection connection, MybatisProperties mybatisProperties)，
 *      实例化 SqlSession 的链接工厂，首先可以看到入参信息包括了我们的这里创建的 connection，另外还有配置入参信息。
 * 最后是内部类 AutoConfiguredMapperScannerRegistrar 的实现，注册 Bean 信息是优先于 Bean 实例化的，
 *      所以这里需要通过实现 EnvironmentAware 读取 yml 中的配置信息，因为这个配置信息是用于扫描 DAO 接口类，注册 Bean 的。
 * @date: 2022/2/23 10:21
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class})
@EnableConfigurationProperties(MybatisProperties.class)
public class MybatisAutoConfiguration implements InitializingBean {

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(Connection connection, MybatisProperties mybatisProperties) throws Exception {
        return new SqlSessionFactoryBuilder().build(connection, mybatisProperties.getMapperLocations());
    }

    @Bean
    @ConditionalOnMissingBean
    public Connection connection(MybatisProperties mybatisProperties) {
        try {
            Class.forName(mybatisProperties.getDriver());
            return DriverManager.getConnection(mybatisProperties.getUrl(), mybatisProperties.getUsername(), mybatisProperties.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class AutoConfiguredMapperScannerRegistrar implements EnvironmentAware, ImportBeanDefinitionRegistrar {

        private String basePackage;

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
            builder.addPropertyValue("basePackage", basePackage);
            registry.registerBeanDefinition(MapperScannerConfigurer.class.getName(), builder.getBeanDefinition());
        }

        @Override
        public void setEnvironment(Environment environment) {
            this.basePackage = environment.getProperty("mybatis.datasource.base-dao-package");
        }
    }

    /**
     * 内部类
     * 用于扫描加载配置和初始化的类启动起来。
     * 主要通过引入 @Import(AutoConfiguredMapperScannerRegistrar.class) 在实现了 InitializingBean 类上，就可以被 Spring 处理了。
     */
    @Configuration
    @Import(AutoConfiguredMapperScannerRegistrar.class)
    @ConditionalOnMissingBean({MapperFactoryBean.class, MapperScannerConfigurer.class})
    public static class MapperScannerRegistrarNotFoundConfiguration implements InitializingBean {

        @Override
        public void afterPropertiesSet() {
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
