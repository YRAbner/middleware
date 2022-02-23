package com.myitem.middleware.mybatis.spring;

import com.myitem.middleware.mybatis.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;
import java.io.IOException;


/**
 * @author: yr
 * @desc: 扫描注入
 * 1、这个类要处理的核心内容，就是把 DAO 接口全部扫描出来，完成代理和注册到 Spring Bean 容器。
 * 2、类的扫描注册，classpath:com/myitem/demo/dao/*.class解析calss文件获取资源信息；
 *      Resource[]resources=resourcePatternResolver.getResources(packageSearchPath);
 * 3、遍历 Resource，这里就你的class 信息，用于注册 bean。ScannedGenericBeanDefinition
 * 4、这里有一点，bean的定义设置时候，是把 beanDefinition.setBeanClass(MapperFactoryBean.class);
 *      设置进去的。同时在前面给他设置了构造参数。
 * 5、最后执行注册 registry.registerBeanDefinition(beanName,definitionHolder.getBeanDefinition());
 * @date: 2022/2/23 9:11
 */
public class MapperScannerConfigurer implements BeanDefinitionRegistryPostProcessor {
    private String basePackage;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        try {
            // classpath*:cn/bugstack/**/dao/**/*.class
            //拼接dao接口的路径
            String packageSearchPath = "classpath*:" + basePackage.replace('.', '/') + "/**/*.class";

            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);

            for (Resource resource : resources) {
                MetadataReader metadataReader = new SimpleMetadataReader(resource, ClassUtils.getDefaultClassLoader());

                ScannedGenericBeanDefinition beanDefinition = new ScannedGenericBeanDefinition(metadataReader);
                String beanName = Introspector.decapitalize(ClassUtils.getShortName(beanDefinition.getBeanClassName()));

                beanDefinition.setResource(resource);
                beanDefinition.setSource(resource);
                beanDefinition.setScope("singleton");
                beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
                beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(sqlSessionFactory);
                beanDefinition.setBeanClass(MapperFactoryBean.class);

                BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(beanDefinition, beanName);
                registry.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // left intentionally blank
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
