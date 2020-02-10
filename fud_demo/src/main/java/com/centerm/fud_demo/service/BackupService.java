package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.BackupRecord;

import java.util.List;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/2/4 上午10:31
 */
public interface BackupService {
    /**
     * 获取所有备份文件
     * @return
     */
    List<BackupRecord> getAllBackup();

    /**
     * 添加备份文件记录
     * @param backupRecord
     * @return
     */
    Boolean addBackupRecord(BackupRecord backupRecord);

    /**
     * 删除备份
     * @param fileId 文件id
     * @return
     */
    Boolean deleteBackup(Long fileId);
}
