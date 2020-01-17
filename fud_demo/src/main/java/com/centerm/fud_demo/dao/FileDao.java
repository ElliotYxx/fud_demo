package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.File;

import java.util.List;

public interface FileDao {
    List<File> getAllFileByUsername(String username);

}
