package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.BackupRecord;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.service.BackupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/2/4 上午10:35
 */
@Service
@Slf4j
public class BackupServiceImpl implements BackupService {
    @Autowired
    FileDao fileDao;
    @Override
    public List<BackupRecord> getAllBackup() {
        return fileDao.getAllBackup();
    }

    @Override
    public Boolean addBackupRecord(BackupRecord backupRecord) {
        return fileDao.addBackupRecord(backupRecord);
    }

    @Override
    public Boolean deleteBackup(Long fileId) {
        BackupRecord backupRecord = fileDao.getBackupById(fileId);
        deleteLocalFile(backupRecord.getLocalUrl());
        return fileDao.deleteBackup(fileId);
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
