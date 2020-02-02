package com.centerm.fud_demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * 下载记录
 * @author jerry
 */
@Data
public class DownloadRecord {
    private Long id;
    private Date create_time;
    private Long userId;
    private Long fileId;
}
