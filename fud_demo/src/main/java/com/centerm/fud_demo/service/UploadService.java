package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.FileRecord;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/30 下午2:17
 */
public interface UploadService {

    /**
     * 文件上传
     * @param file　文件
     * @param chunk　块
     * @param guid　md5值
     * @param uploaderId 上传者id
     * @throws Exception
     */
    void upload(MultipartFile file, Integer chunk, String guid, Long uploaderId) throws Exception;


    /**
     * 获取总上传次数
     * @return
     */
    Long getUploadTimes();

    /**
     * 文件块的合并
     * @param guid md5
     * @param fileName 文件名
     */
    void combineBlock(String guid, String fileName);

    /**
     * 检查md5值
     * @param request
     * @param response
     */
    void checkMd5(HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取最新上传文件的前五个
     * @return
     */
    List<FileRecord> getLatestUploaded();
}
