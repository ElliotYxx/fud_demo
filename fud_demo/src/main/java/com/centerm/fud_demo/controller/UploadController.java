package com.centerm.fud_demo.controller;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.FileService;
import com.centerm.fud_demo.service.UploadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

    User currUser = null;

    @Autowired
    UploadService uploadService;
    @Autowired
    FileService fileService;

    /**
     * 跳转到上传界面
     * @return
     */
    @GetMapping("index")
    public String toUpload() {
        return "uploadbackup";
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
     * @param file 文件
     * @param chunk　块
     * @param guid　md5标识
     * @throws IOException
     */
    @PostMapping("save")
    @ResponseBody
    public void upload(@RequestParam MultipartFile file, Integer chunk, String guid, HttpServletRequest request) throws Exception {
        currUser = (User) request.getSession().getAttribute("user");
        uploadService.upload(file, chunk, guid, currUser.getId());
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

    /**
     * @param fileId 文件id
     * @return
     */
    @ApiOperation("删除文件")
    @GetMapping("toDelete")
    public ModelAndView toDelete(Long fileId, HttpServletRequest request) {
        ModelAndView mv=new ModelAndView();
        currUser = (User) request.getSession().getAttribute("user");
        System.out.println("当前用户id为：" +currUser.getId());
        fileService.deleteFileById(currUser.getId(), fileId);
        mv.setViewName("redirect:/user/filemanager");
        return mv;
    }
}
