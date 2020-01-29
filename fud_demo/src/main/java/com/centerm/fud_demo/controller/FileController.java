package com.centerm.fud_demo.controller;

import java.util.Date;
import java.util.List;

import com.centerm.fud_demo.entity.File;
import com.centerm.fud_demo.service.FileService;
import com.centerm.fud_demo.service.UserService;
import com.centerm.fud_demo.utils.EncryptUtil;
import com.centerm.fud_demo.utils.FileUtil;
import com.centerm.fud_demo.utils.VerificationUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * File相关操作控制类
 * @author sheva
 */
@Controller
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Value("${filePath}")
    private String filePath;
    @Value("${backupPath}")
    private String backupPath;
    @Value("${downloadPath}")
    private String downloadPath;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;


    @ApiOperation("上传文件")
    @GetMapping("/toUser_uploading")
    public String uploading() {
        return "logged/user_uploading";
    }

    /**
     * 文件的上传
     * @param srcFile  要上传的源文件
     * @param redirectAttributes  重定向参数（返回操作结果的相关信息）
     * @return
     */
    @ApiOperation("上传文件")
    @PostMapping("/uploading")
    public String uploading(@RequestParam("file") MultipartFile srcFile,
                            RedirectAttributes redirectAttributes) {

        if (srcFile.isEmpty()){
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload!");
        }
        File file = null;
        try {
            String fileMD5 = VerificationUtil.getMD5String(srcFile.getBytes());
            log.info("上传的文件的md5值为：" + fileMD5);
            String fileName = srcFile.getOriginalFilename();
            String fileSize = Long.toString(srcFile.getSize());
            //暂且当user_id均为1
            file = new File(fileName, filePath + fileName, fileSize, 1, fileMD5, new Date());
            redirectAttributes.addFlashAttribute("message", "file upload successfully     " + fileName);
            FileUtil.uploadFile(EncryptUtil.encrypt(srcFile.getBytes()), filePath, srcFile.getOriginalFilename());
            log.info("文件上传成功");
            FileUtil.backupFile(EncryptUtil.encrypt(srcFile.getBytes()), backupPath, srcFile.getOriginalFilename());
            log.info("文件备份成功");
            fileService.addFile(file);
//            log.info("加密前：　　" + new String(srcFile.getBytes()));
//            log.info("加密后：  " + new String(EncryptUtil.encrypt(srcFile.getBytes())));
//            log.info("解密后：  " + new String(EncryptUtil.decrypt(EncryptUtil.encrypt(srcFile.getBytes()))));
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "file upload failed");
        }
        return "redirect:uploadStatus";
    }

    @ApiOperation("文件上传完成结果")
    @GetMapping("/uploadStatus")
    public String uploadStatus(){
        return "logged/upload_status";
    }

    @ApiOperation("文件列表")
    @RequestMapping("/list")
    public String list(Model model){
        List<com.centerm.fud_demo.entity.File> fileList=fileService.getAllFileByUsername("test");
        model.addAttribute("fileList", fileList);
        return "logged/user_download";
    }


    /**
     * 有待测试
     * @param id
     * @param request
     * @param response
     * @return
     */
    @ApiOperation("下载文件")
    @RequestMapping("/toDownload")
    public String toDownload(Integer id, HttpServletRequest request, HttpServletResponse response)
    {

        File file = fileService.getFileById(id);

        FileUtil.downloadFile(file, downloadPath, request, response);
        return "logged/user_download";
    }


}


