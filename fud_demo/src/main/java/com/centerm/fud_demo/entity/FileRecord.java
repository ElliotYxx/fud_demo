package com.centerm.fud_demo.entity;

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
    private String localUrl;
    private String size;
    private String description;
    private Long downloadTimes;
    private Long userId;
    private String md5;
    private Date createTime;
    private String suffix;

    public FileRecord(){}

    public FileRecord(String name, String size, String suffix, Date createTime, Long downloadTimes) {
        this.name = name;
        this.size = size;
        this.suffix = suffix;
        this.createTime = createTime;
        this.downloadTimes = downloadTimes;
    }


    public FileRecord(String name, String localUrl, String size, Long userId, String md5, String suffix, Date createTime) {
        this.name = name;
        this.localUrl = localUrl;
        this.size = size;
        this.userId = userId;
        this.md5 = md5;
        this.suffix = suffix;
        this.createTime = createTime;
    }


    public FileRecord(Long id, String name, String localUrl, Date createTime) {
        this.id = id;
        this.name = name;
        this.localUrl = localUrl;
        this.createTime = createTime;
    }

    public FileRecord(Long id, String name, String size, String suffix, Date createTime) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.suffix = suffix;
        this.createTime = createTime;
    }
}
