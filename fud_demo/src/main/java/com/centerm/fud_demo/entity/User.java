package com.centerm.fud_demo.entity;

import lombok.Data;

import java.util.Date;

@Data

public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer permission;
    private Date create_time;
    //salt 为用户名

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
