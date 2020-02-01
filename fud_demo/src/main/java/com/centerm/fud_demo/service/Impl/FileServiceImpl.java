package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件相关操作实现类
 * @author sheva
 */
@Service
public class FileServiceImpl implements FileService {

    @Value("${filePath}")
    private String filePath;
    @Value("${backupPath}")
    private String backupPath;
    @Value("${downloadPath}")
    private String downloadPath;

    @Autowired
    private FileDao fileDao;

    @Override
    public List<FileRecord> getAllFileByUsername(String username) {
        return fileDao.getAllFileByUsername(username);
    }

    @Override
    public Boolean addFile(FileRecord fileRecord) {
        return fileDao.addFile(fileRecord);
    }

    @Override
    public FileRecord getFileById(Long id) {
        return fileDao.getFileById(id);
    }

    @Override
    public List<FileRecord> getAllFile() {
        return fileDao.getAllFile();
    }

    @Override
    public List<Integer> getDownloadNumbers() {
        return fileDao.getDownloadNumbers();
    }


}
