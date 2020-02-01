package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.FileRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * File相关操作映射
 * @author sheva
 */
@Mapper
public interface FileDao {
   /**
    * 根据用户名查询上传的文件
    * @param username 用户名
    * @return 文件记录集合
    */
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

   /**
    * 获取最热门下载
    * @return fileRecord集合
    */
   List<FileRecord> getMostDownloadRecord();

   /**
    * 获取某个用户上传的文件的总下载次数
    * @param userId 用户id
    * @return 总下载次数
    */
   Long getDownloadTimesByUserId(Long userId);

   Long getDownloadTimes();

   Long getUploadTimes();
}
