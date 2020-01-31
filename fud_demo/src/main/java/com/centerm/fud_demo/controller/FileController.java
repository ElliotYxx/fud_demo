package com.centerm.fud_demo.controller;

import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ch.qos.logback.classic.Logger;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * File相关操作控制类
 * @author sheva
 */
@Controller
@RequestMapping("file")
@Slf4j
public class FileController {

    @Value("${filePath}")
    private String uploadPath;
    @Value("${backupPath}")
    private String backupPath;
    @Value("${downloadPath}")
    private String downloadPath;

    private static Logger logger = (Logger) LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;



    @ApiOperation("文件列表")
    @RequestMapping("list")
    public String list(Model model){
        List<FileRecord> fileList=fileService.getAllFileByUsername("test");
        model.addAttribute("fileList", fileList);
        return "logged/user_index";
    }



}


