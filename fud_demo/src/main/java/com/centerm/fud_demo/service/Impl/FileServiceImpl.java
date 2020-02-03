package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * 文件相关操作实现类
 * @author sheva
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;


    @Override
    public List<FileRecord> getFileByUserId(Long userId) {
        return fileDao.getFileByUserId(userId);
    }


    @Override
    public Boolean addFile(FileRecord fileRecord) {
        return fileDao.addFile(fileRecord);
    }

    @Override
    public Boolean deleteFileById(Long userId, Long fileId) {
        FileRecord deleteFile = fileDao.getFileById(fileId);
        deleteLocalFile(deleteFile.getLocalUrl());
        return fileDao.deleteFileById(userId, fileId);
    }

    @Override
    public Boolean deleteFile(Long fileId) {
        FileRecord deleteFile = fileDao.getFileById(fileId);
        deleteLocalFile(deleteFile.getLocalUrl());
        return fileDao.deleteFile(fileId);
    }

    @Override
    public List<FileRecord> getAllFile() {
        return fileDao.getAllFile();
    }

    @Override
    public Boolean updateFile(Long fileId) {
        return fileDao.updateFile(fileId);
    }


    private void deleteLocalFile(String localUrl) {
        try{
            System.out.println("开始删除本地文件: " + localUrl);
            File deleteFile = new File(localUrl);
            deleteFile.delete();
        }catch (Exception e){
            System.out.println("删除本地文件出错");
            e.printStackTrace();
        }
    }

}
