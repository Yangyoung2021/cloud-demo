package com.yang.test;

import lombok.Data;

import java.util.Date;

@Data
public class UserFo {
    private Integer age;
    private String name;
    private String address;
    private Date createTime;
    private String isTall;
}