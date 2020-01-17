package com.centerm.fud_demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class File {
    private Integer id;
    private String name;
    private String local_url;
    private Double size;
    private String description;
    private Integer download_times;
    private Integer user_id;
    private Date create_time;
}
