package com.centerm.fud_demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/2/4 上午10:29
 */
@Getter
@Setter
public class BackupRecord {
    private Long fileId;
    private String name;
    private String localUrl;
    private Long userId;
    private Timestamp createTime;

    public BackupRecord(){}

    public BackupRecord(Long fileId, String name, String localUrl, Long userId, Timestamp createTime) {
        this.fileId = fileId;
        this.name = name;
        this.localUrl = localUrl;
        this.userId = userId;
        this.createTime = createTime;
    }
}
