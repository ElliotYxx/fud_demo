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
     * 根据用户id获取文件
     * @param userId
     * @return
     */
    List<FileRecord> getFileByUserId(Long userId);



    /**
     * 添加文件
     * @param fileRecord 需要上传的文件
     * @return
     */
    Boolean addFile(FileRecord fileRecord);

    /**
     * 根据用户id和文件id删除文件
     * @param userId 用户id
     * @param fileId 文件id
     * @return
     */
    Boolean deleteFileById(Long userId, Long fileId);

    /**
     * 根据文件id删除文件（管理员操作）
     * @param fileId 文件id
     * @return
     */
    Boolean deleteFile(Long fileId);

    /**
     * 获取所有文件
     * @return
     */
    List<FileRecord> getAllFile();

    /**
     * 更新文件（downloadTimes加1）
     * @param fileId 文件id
     * @return
     */
    Boolean updateFile(Long fileId);





}
