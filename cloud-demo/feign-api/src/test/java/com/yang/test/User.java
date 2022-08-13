package com.yang.test;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;
    private Integer age;
    private String name;
    private Date createTime;
    private String address;
}