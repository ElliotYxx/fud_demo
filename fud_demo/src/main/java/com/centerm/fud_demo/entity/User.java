package com.centerm.fud_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private Date createTime;
    private Integer state;
    private String stateName;
    private Integer isOnline;
    private Long roleId;
    private String role;
    //salt 为用户名

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password, Date createTime, Integer state) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.state = state;
    }

    public User(Long id, String username, String password, Date createTime, Integer state, Integer isOnline) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.state = state;
        this.isOnline = isOnline;
    }

}