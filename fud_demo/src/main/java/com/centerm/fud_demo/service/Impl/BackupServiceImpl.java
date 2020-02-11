package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.BackupRecord;
import com.centerm.fud_demo.service.BackupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
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
    private FileDao fileDao;
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
            log.info("开始删除本地备份文件: " + localUrl);
            File deleteFile = new File(localUrl);
            deleteFile.delete();
            log.info("删除成功...");
        }catch (Exception e){
            log.info("删除本地备份文件出错...");
            e.printStackTrace();
        }
    }
}
