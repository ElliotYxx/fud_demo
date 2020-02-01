package com.centerm.fud_demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DownloadRecord {
    private Long id;
    private Date create_time;
    private Long user_id;
    private Long file_id;
}
