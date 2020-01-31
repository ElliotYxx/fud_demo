package com.centerm.fud_demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/31 下午12:34
 */
public interface DownloadService {
    /**
     * 下载文件
     * @param id　　文件id
     * @param response　　
     * @param request
     * @throws Exception
     */
    void downloadFile(Long id, HttpServletResponse response, HttpServletRequest request)
            throws Exception;
}
