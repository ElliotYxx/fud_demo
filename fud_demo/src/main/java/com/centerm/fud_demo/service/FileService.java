package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.FileRecord;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * File相关操作接口
 * @author sheva
 */
public interface FileService {
    /**
     * 根据用户名来查询其上传的文件
     * @param username 用户名
     * @return
     */
    List<FileRecord> getAllFileByUsername(String username);

    /**
     * 添加文件
     * @param fileRecord 需要上传的文件
     * @return
     */
    Boolean addFile(FileRecord fileRecord);

    /**
     * 通过id获取文件
     * @param id 文件id
     * @return file实体类
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
