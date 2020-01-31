package com.centerm.fud_demo.controller;

import com.centerm.fud_demo.service.DownloadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/31 下午12:41
 */

@Controller
@RequestMapping("download")
public class DownloadController {

    @Autowired
    DownloadService downloadService;

    /**
     * 有待测试
     * @param id
     * @param response
     * @return
     */
    @ApiOperation("下载文件")
    @RequestMapping("toDownload")
    public String toDownload(Long id, HttpServletResponse response, HttpServletRequest request) throws Exception {
        downloadService.downloadFile(id, response, request);
        return "logged/user_index";
    }
}
