package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.FileRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * File相关操作映射
 * @author sheva
 */
@Component
public interface FileDao {

   /**
    * 根据用户id获取上传文件
    * @param userId
    * @return
    */
   List<FileRecord> getFileByUserId(Long userId);
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

   /**
    * 获取下载次数
    * @return 下载次数
    */
   Long getDownloadTimes();

   /**
    * 获取上传次数
    * @return
    */
   Long getUploadTimes();

   /**
    * 根据用户id与文件id删除文件
    * @param userId
    * @param fileId
    * @return
    */
   Boolean deleteFileById(Long userId, Long fileId);

   /**
    * 根据文件id删除文件（管理员操作）
    * @param fileId
    * @return
    */
   Boolean deleteFile(Long fileId);


   /**
    * 更新文件信息（下载次数）
    * @param fileId 文件id
    * @return
    */
   Boolean updateFile(Long fileId);

   /**
    * 删除下载信息
    * @param fileId 文件id
    * @return
    */
   Boolean deleteDownloadRecord(Long fileId);

   /**
    * 获取最新上传的前五个文件
    * @return 文件集合
    */
   List<FileRecord> getLatestUploaded();

   /**
    * 获取最新下载的前五个文件
    * @return 文件集合
    */
   List<FileRecord> getLatestDownloaded();
}
