package com.centerm.fud_demo.controller;
import com.centerm.fud_demo.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 分片上传Controller
 * @author sheva
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    @Autowired
    UploadService uploadService;


    /**
     * 跳转到首页
     *
     * @return
     */
    @GetMapping("index")
    public String toUpload() {
        return "/logged/user_uploading";
    }

    /**
     * 查看当前分片是否上传
     *
     * @param request
     * @param response
     */
    @PostMapping("checkblock")
    @ResponseBody
    public void checkMd5(HttpServletRequest request, HttpServletResponse response) {
        uploadService.checkMd5(request, response);
    }

    /**
     * 上传分片
     *
     * @param file
     * @param chunk
     * @param guid
     * @throws IOException
     */
    @PostMapping("save")
    @ResponseBody
    public void upload(@RequestParam MultipartFile file, Integer chunk, String guid) throws Exception {
        uploadService.upload(file, chunk, guid);
    }
    /**
     * 合并文件
     * @param guid　md5
     * @param fileName 文件名
     */
    @PostMapping("combine")
    @ResponseBody
    public void combineBlock(String guid, String fileName) {
        uploadService.combineBlock(guid, fileName);
    }
}
