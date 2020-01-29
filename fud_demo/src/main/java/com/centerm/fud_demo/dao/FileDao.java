package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
public interface FileDao {
   List<File> getAllFileByUsername(String username);
   /**
    * 添加文件
    * @param file 文件实体
    * @return 文件
    */
   Boolean addFile(File file);
   /**
    * 根据id获取文件
    * @param id  文件id
    * @return
    */
   File getFileById(Integer id);
   List<File> getAllFile();
   List<Integer> getDownloadNumbers();
}
