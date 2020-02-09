package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.service.DownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/31 下午12:35
 */
@Service
@Slf4j
public class DownloadServiceImpl implements DownloadService {

    @Autowired
    private FileDao fileDao;

    @Override
    public Boolean addDownloadRecord(DownloadRecord downloadRecord) {
        return fileDao.addDownloadRecord(downloadRecord);
    }

    @Override
    public List<FileRecord> getMostDownloadRecord() {
        return fileDao.getMostDownloadRecord();
    }

    @Override
    public Long getDownloadTimesByUserId(Long userId) {
        return fileDao.getDownloadTimesByUserId(userId);
    }

    @Override
    public Long getDownloadTimes() {
        return fileDao.getDownloadTimes();
    }

    @Override
    public Boolean deleteDownloadRecord(Long fileId) {
        return fileDao.deleteDownloadRecord(fileId);
    }

    @Override
    public List<FileRecord> getLatestDownloaded() {
        return fileDao.getLatestDownloaded();
    }

    @Override
    public void downloadFile(Long id, HttpServletResponse response) {
        FileRecord downloadFile = fileDao.getFileById(id);
        File file = new File(downloadFile.getLocalUrl());
        if (!file.exists()){
            log.error("文件不存在...");
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
            log.info("开始下载: " + downloadFile.getName());
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while((i = bis.read(buff)) != -1){
                os.write(buff, 0, i);
                os.flush();
            }
            log.info("下载成功...");
        }catch (IOException e){
            log.error("当前用户取消了下载...");
        }

    }
}
