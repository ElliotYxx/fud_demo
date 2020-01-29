package com.centerm.fud_demo.service;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
    List<File> getAllFileByUsername(String username);

    /**
     * 添加文件
     * @param file 需要上传的文件
     * @return
     */
    Boolean addFile(File file);

    /**
     * 通过id获取文件
     * @param id 文件id
     * @return file实体类
     */
    File getFileById(Integer id);

    List<File> getAllFile();

    List<Integer> getDownloadNumbers();
}
