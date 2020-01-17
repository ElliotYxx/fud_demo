package com.centerm.fud_demo.controller;

import java.io.File;
import java.io.FileOutputStream;

import com.centerm.fud_demo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import static com.centerm.fud_demo.utils.FileUtil.uploadFile;

/**
 * Class description
 *
 * @author         Jerry
 * @date           20/01/17
 * @version         1.0
 */
@Controller
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Value("${filePath}")
    private String filePath;


    @GetMapping("/upload")
    public String uploading() {
        return "uploading";
    }

    @PostMapping("/uploading")
    @ResponseBody
    public String uploading(@RequestParam("file") MultipartFile file) {
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("文件上传失败");

            return "uploading failure";
        }

        log.info("文件上传成功");

        return "uploading success";
    }
    @RequestMapping("getAllFileByUsername")
    public Boolean getAllFileByUsername()
    {

    }


}


//~ Formatted by Jindent --- http://www.jindent.com
