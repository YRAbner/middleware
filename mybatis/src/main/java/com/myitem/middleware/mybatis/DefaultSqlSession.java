package com.myitem.middleware.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 22:56
 */
public class DefaultSqlSession implements SqlSession{
    private Connection connection;
    private Map<String , XNode> mapperElement;

    public DefaultSqlSession(Connection connection, Map<String, XNode> mapperElement) {
        this.connection = connection;
        this.mapperElement = mapperElement;
    }

    @Override
    public <T> T selectOne(String statement) {
        try {
            XNode xNode = mapperElement.get(statement);
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> objects = (List<T>) resultSet2Obj(resultSet , Class.forName(xNode.getResultType()));
            return objects.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        try {
            XNode xNode = mapperElement.get(statement);
            Map<Integer , String> parameterMap = xNode.getParameter();
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            buileParameter(preparedStatement , parameter , parameterMap);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> objects = (List<T>) resultSet2Obj(resultSet , Class.forName(xNode.getResultType()));
            return objects.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statement) {
        try {
            XNode xNode = mapperElement.get(statement);
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            ResultSet resultSet = preparedStatement.executeQuery();
            return (List<T>) resultSet2Obj(resultSet , Class.forName(xNode.getResultType()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statement, Object parameter) {
        try {
            XNode xNode = mapperElement.get(statement);
            Map<Integer , String> parameterMap = xNode.getParameter();
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            buileParameter(preparedStatement , parameter , parameterMap);
            ResultSet resultSet = preparedStatement.executeQuery();
            return (List<T>) resultSet2Obj(resultSet , Class.forName(xNode.getResultType()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private <T>List<T> resultSet2Obj(ResultSet resultSet , Class<T> clazz){
        List<T> list = new ArrayList<>();
        try{
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int cloumnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()){
                T obj = clazz.newInstance();
                for (int i = 1; i <= cloumnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String columnName = resultSetMetaData.getColumnName(i);
                    String setMethod = "set" + columnName.substring(0,1).toUpperCase() + columnName.substring(1);
                    Method method;
                    if (value instanceof Timestamp){
                        method = clazz.getMethod(setMethod , java.util.Date.class);
                    }else {
                        method = clazz.getMethod(setMethod , value.getClass());
                    }
                    method.invoke(obj , value);
                }
                list.add(obj);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private void buileParameter(PreparedStatement preparedStatement , Object parameter , Map<Integer , String> parameterMap) throws SQLException, IllegalAccessException {
        int size = parameterMap.size();
        if (parameter instanceof Long){
            for (int i = 1; i <= size; i++) {
                preparedStatement.setLong(i , Long.parseLong(parameter.toString()));
            }
            return;
        }
        if (parameter instanceof Integer){
            for (int i = 1; i <= size ; i++) {
                preparedStatement.setInt(i , Integer.parseInt(parameter.toString()));
            }
            return;
        }
        if (parameter instanceof String){
            for (int i = 1; i <= size; i++) {
                preparedStatement.setString(i , parameter.toString());
            }
            return;
        }

        Map<String , Object> fieldMap = new HashMap<>();
        Field[] fields = parameter.getClass().getFields();
        for (Field field : fields){
            String name = field.getName();
            field.setAccessible(true);
            Object obj = field.get(parameter);
            field.setAccessible(false);
            fieldMap.put(name , obj);
        }

        for (int i = 1; i <= size ; i++) {
            String parameterDefine = parameterMap.get(i);
            Object obj = fieldMap.get(parameterDefine);

            if (obj instanceof Short){
                preparedStatement.setShort(i , Short.parseShort(obj.toString()));
                continue;
            }
            if (obj instanceof Integer){
                preparedStatement.setInt(i , Integer.parseInt(obj.toString()));
                continue;
            }
            if (obj instanceof Long){
                preparedStatement.setLong(i , Long.parseLong(obj.toString()));
                continue;
            }
            if (obj instanceof String){
                preparedStatement.setString(i , obj.toString());
            }
            if (obj instanceof Date){
                preparedStatement.setDate(i , (java.sql.Date) obj);
            }
        }

    }
    @Override
    public void close() {
        try{
            if (null == connection){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
