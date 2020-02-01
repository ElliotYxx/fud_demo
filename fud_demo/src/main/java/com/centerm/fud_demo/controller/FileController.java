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
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


/**
 * File相关操作控制类
 * @author sheva
 */
@Controller
@RequestMapping("file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    User user = null;

    @ApiOperation("文件列表")
    @RequestMapping("list")
    public String list(Model model, HttpServletRequest request){
        user = (User) request.getSession().getAttribute("user");
        log.info("user的角色是： " + user.getRole());
        List<FileRecord> fileList=fileService.getAllFileByUsername(user.getUsername());
        model.addAttribute("fileList", fileList);
        return "logged/user_index";
    }
}


