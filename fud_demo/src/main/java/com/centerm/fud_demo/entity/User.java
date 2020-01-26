package com.centerm.fud_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Date create_time;
    private Integer state;
    private Integer is_Online;
    private String role;
    //salt 为用户名

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public User(Integer id, String username, String password, Date create_time, Integer state) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.create_time = create_time;
        this.state = state;
    }

    public User(Integer id, String username, String password, Date create_time, Integer state, Integer is_Online) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.create_time = create_time;
        this.state = state;
        this.is_Online = is_Online;
    }
}
