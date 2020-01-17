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
}
