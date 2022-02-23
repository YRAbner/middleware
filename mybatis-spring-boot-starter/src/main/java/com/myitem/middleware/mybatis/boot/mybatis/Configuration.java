package com.myitem.middleware.mybatis.boot.mybatis;

import java.sql.Connection;
import java.util.Map;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/23 10:22
 */
public class Configuration {
    //连接bean
    protected Connection connection;
    //连接信息
    protected Map<String, String> dataSource;
    //mapper参数
    protected Map<String, XNode> mapperElement;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Map<String, String> getDataSource() {
        return dataSource;
    }

    public void setDataSource(Map<String, String> dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, XNode> getMapperElement() {
        return mapperElement;
    }

    public void setMapperElement(Map<String, XNode> mapperElement) {
        this.mapperElement = mapperElement;
    }
}
