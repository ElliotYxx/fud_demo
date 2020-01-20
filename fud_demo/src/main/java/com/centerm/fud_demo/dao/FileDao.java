package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.File;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface FileDao {
   public  List<File> getAllFileByUsername(String username);
   public  List<File> getAllFile();
   public List<Integer> getDownloadNumbers();
}
