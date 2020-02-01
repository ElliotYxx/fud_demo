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
public class FileRecord {
    private Long id;
    private String name;
    private String local_url;
    private String size;
    private String description;
    private Long download_times;
    private Long user_id;
    private String md5;
    private Date create_time;
    private String suffix;

    public FileRecord(String name, String size, Long download_times, String suffix, Date create_time) {
        this.name = name;
        this.size = size;
        this.download_times = download_times;
        this.suffix = suffix;
        this.create_time = create_time;
    }

    public FileRecord(){}
    public FileRecord(String name, String local_url, String size, Long user_id, String md5, String suffix, Date create_time) {
        this.name = name;
        this.local_url = local_url;
        this.size = size;
        this.user_id = user_id;
        this.md5 = md5;
        this.suffix = suffix;
        this.create_time = create_time;
    }


    public FileRecord(Long id, String name, String suffix, String size, Long download_times, Date create_time) {
        this.id = id;
        this.name = name;
        this.suffix = suffix;
        this.size = size;
        this.download_times = download_times;
        this.create_time = create_time;
    }

    public FileRecord(Long id, String name, String local_url, Date create_time) {
        this.id = id;
        this.name = name;
        this.local_url = local_url;
        this.create_time = create_time;
    }
}
