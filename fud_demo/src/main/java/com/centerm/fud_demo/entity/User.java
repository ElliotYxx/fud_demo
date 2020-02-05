package com.centerm.fud_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.terracotta.statistics.Time;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private Timestamp createTime;
    private Integer state;
    private Integer isOnline;
    private Long roleId;
    //salt 为用户名

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


}