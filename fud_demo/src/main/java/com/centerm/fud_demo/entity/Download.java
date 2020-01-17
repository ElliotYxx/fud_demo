package com.centerm.fud_demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Download {
    private Integer id;
    private Date create_time;
    private Integer user_id;
    private Integer file_id;
}
