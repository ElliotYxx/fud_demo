package com.centerm.fud_demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * 文件实体类
 * @author sheva
 */
@Getter
@Setter
public class File {
    private Integer id;
    private String name;
    private String local_url;
    private String size;
    private String description;
    private Integer download_times;
    private Integer user_id;
    private String md5;
    private Date create_time;


    public File(){}

    public File(String name, String local_url, String size, Integer user_id, String md5, Date create_time) {
        this.name = name;
        this.local_url = local_url;
        this.size = size;
        this.user_id = user_id;
        this.md5 = md5;
        this.create_time = create_time;
    }

    public File(String name, String size, String description, Date create_time) {
        this.name = name;
        this.size = size;
        this.description = description;
        this.create_time = create_time;
    }


}
