package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.FileRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface FileDao {
   List<FileRecord> getAllFileByUsername(String username);
   /**
    * 添加文件
    * @param file 文件实体
    * @return 文件
    */
   Boolean addFile(FileRecord file);
   /**
    * 根据id获取文件
    * @param id  文件id
    * @return
    */
   FileRecord getFileById(Long id);

   /**
    * 获取所有文件
    * @return
    */
   List<FileRecord> getAllFile();
   List<Integer> getDownloadNumbers();

   /**
    * 添加下载记录
    * @param downloadRecord
    * @return
    */
   Boolean addDownloadRecord(DownloadRecord downloadRecord);
}
