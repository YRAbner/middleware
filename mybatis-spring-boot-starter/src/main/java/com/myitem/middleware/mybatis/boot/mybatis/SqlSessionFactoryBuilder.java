package com.myitem.middleware.mybatis.boot.mybatis;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.InputSource;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/23 10:22
 */
public class SqlSessionFactoryBuilder {

    //构建工厂 ， 流程基本一直
    public DefaultSqlSessionFactory build(Connection connection, String packageSearchPath) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setConnection(connection);
        // 读取配置
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
        List<Element> list = new ArrayList<>(resources.length);
        for (Resource resource : resources) {
            Document document = new SAXReader().read(new InputSource(new InputStreamReader(resource.getInputStream())));
            list.add(document.getRootElement());
        }
        configuration.setMapperElement(mapperElement(list));
        return new DefaultSqlSessionFactory(configuration);
    }

/*    //配置文件解析
    // 是对xml中的元素进行获取，这里主要获取了；
    // dataSource、mappers，而这两个配置一个是我们数据库的链接信息，
    // 另外一个是对数据库操作语句的解析。
    private Configuration parseConfiguration(Element root) {
        Configuration configuration = new Configuration();
        configuration.setDataSource(dataSource(root.selectNodes("//dataSource")));
        configuration.setConnection(connection(configuration.dataSource));
        configuration.setMapperElement(mapperElement(root.selectNodes("mappers")));
        return configuration;
    }

    // 获取数据源配置信息
    private Map<String, String> dataSource(List<Element> list) {
        Map<String, String> dataSource = new HashMap<>(4);
        Element element = list.get(0);
        List content = element.content();
        for (Object o : content) {
            Element e = (Element) o;
            String name = e.attributeValue("name");
            String value = e.attributeValue("value");
            dataSource.put(name, value);
        }
        return dataSource;
    }

    //连接数据库
    private Connection connection(Map<String, String> dataSource) {
        try {
            Class.forName(dataSource.get("driver"));
            return DriverManager.getConnection(dataSource.get("url"), dataSource.get("username"), dataSource.get("password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    // 获取SQL语句信息（核心处理sql解析）
    private Map<String, XNode> mapperElement(List<Element> list) {
        Map<String, XNode> map = new HashMap<>();
        for (Element root : list) {
            //命名空间
            String namespace = root.attributeValue("namespace");
            // SELECT
            List<Element> selectNodes = root.selectNodes("select");
            for (Element node : selectNodes) {
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                // ? 匹配
                Map<Integer, String> parameter = new HashMap<>();
                Pattern pattern = Pattern.compile("(#\\{(.*?)})");
                Matcher matcher = pattern.matcher(sql);
                for (int i = 1; matcher.find(); i++) {
                    String g1 = matcher.group(1);
                    String g2 = matcher.group(2);
                    parameter.put(i, g2);
                    sql = sql.replace(g1, "?");
                }

                XNode xNode = new XNode();
                xNode.setNamespace(namespace);
                xNode.setId(id);
                xNode.setParameterType(parameterType);
                xNode.setResultType(resultType);
                xNode.setSql(sql);
                xNode.setParameter(parameter);

                map.put(namespace + "." + id, xNode);
            }
        }
        return map;
    }
}
