package com.myitem.middleware.ratelimiter.test.interfaces;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 7:34
 */
public class UserInfo {
    private String username;
    private String address;
    private Integer age;
    private String code;
    private String info;

    public UserInfo() {
    }

    public UserInfo(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public UserInfo(String username, String address, Integer age) {
        this.username = username;
        this.address = address;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
