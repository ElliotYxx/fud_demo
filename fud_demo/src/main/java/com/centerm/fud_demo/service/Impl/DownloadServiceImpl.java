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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/31 下午12:35
 */
@Service
@Slf4j
public class DownloadServiceImpl implements DownloadService {
    @Autowired
    FileDao fileDao;
    @Override
    public void downloadFile(Long id, HttpServletResponse response, HttpServletRequest request) {
        response.reset();
        FileRecord downloadFile = fileDao.getFileById(id);
        File file = new File(downloadFile.getLocal_url());
        response.reset();
        response.setContentLength((int) file.length());

        response.setContentType("application/force-download");
        try{
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" + URLEncoder.encode(downloadFile.getName(), "UTF-8"));
        }catch (UnsupportedEncodingException e){
            log.error("UnsupportedEncodingException...");
            log.error(e.getMessage());
        }
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try{
            log.info("download start....");
            fileInputStream = new FileInputStream(file);
            byte[] buffers = new byte[1024];
            outputStream = response.getOutputStream();
            int length;
            while((length = fileInputStream.read(buffers)) > 0){
                outputStream.write(buffers, 0, length);
            }
            log.info("download finished...");
        }catch (IOException e){
            log.error("IOException...");
            log.error(e.getMessage());
        }finally {
            if (fileInputStream != null){
                try{
                    fileInputStream.close();
                }catch (IOException e){
                    log.error("IOException");
                    log.error(e.getMessage());
                }
            }
            if (outputStream != null){
                try{
                    outputStream.flush();
                    outputStream.close();
                }catch (IOException e){
                    log.error("IOException");
                    log.error(e.getMessage());
                }
            }
        }
    }
}
