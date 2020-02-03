package com.centerm.fud_demo.controller;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.exception.AccountBanException;
import com.centerm.fud_demo.listener.Listener;
import com.centerm.fud_demo.service.*;
import com.centerm.fud_demo.shiro.UserRealm;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 管理员控制类
 * @author jerry
 */
@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController {

    static final Integer BAN = 1;
    @Autowired
    AdminService adminService;
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;
    @Autowired
    DownloadService downloadService;
    @Autowired
    UploadService uploadService;


    @GetMapping("file")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String adminDownload(HttpServletRequest request)
    {
        List<FileRecord> fileList=fileService.getAllFile();
        request.setAttribute("fileList",fileList);
        return "admin/filelist";
    }

    @GetMapping("index")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String adminIndex(ServletRequest request)
    {
        AtomicInteger userNum=Listener.sessionCount;
        long fileNums = uploadService.getUploadTimes();
        Long downloadTimes = downloadService.getDownloadTimes();
        List<FileRecord> fileRecordList = downloadService.getMostDownloadRecord();
        request.setAttribute("userNum",userNum);
        request.setAttribute("fileNums", fileNums);
        request.setAttribute("downloadTimes", downloadTimes);
        request.setAttribute("fileList", fileRecordList);
        return "admin/index";
    }


    @GetMapping("ban")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String adminBan(HttpServletRequest request) {
        User user=(User)request.getSession().getAttribute("user");
        Long userId=user.getId();
        List<User> userList = adminService.getUserExceptAdminAndSuperVIP(userId);
        for (User currUSer : userList) {
            if (currUSer.getState().equals(BAN)) {
                currUSer.setStateName("封禁");
            }else{
                currUSer.setStateName("正常");
            }
        }
        request.setAttribute("userList",userList);
        return "admin/ban";
    }

    @RequestMapping("banUser")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public ModelAndView banUser(HttpServletRequest request)
            throws AccountBanException
    {
        ModelAndView mv=new ModelAndView();
        String username=request.getParameter("username");
        User target=userService.findByUsername(username);
        Integer userState = target.getState();
        Long userId=target.getId();
       if(userState.equals(0))
       {
           //执行账号封禁
           Boolean isSuccess= adminService.banUser(userId);
           if (isSuccess.equals(0))
           {
               throw new AccountBanException();
           }
           log.info("用户 "+username+"　被封禁");
       }else {
           //执行账号解锁
           Boolean isSuccess = adminService.releaseUser(userId);
           if (isSuccess.equals(0))
           {
               throw new AccountBanException();
           }
           log.info("用户 "+username+"　被解除封禁");
       }
        DefaultWebSecurityManager securityManager;
        securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm shiroRealm = (UserRealm) securityManager.getRealms().iterator().next();
        shiroRealm.clearAllCache();
        mv.setViewName("redirect:/admin/ban");
        return mv;
    }

    /**
     * @param fileId 文件id
     * @return
     */
    @ApiOperation("删除文件")
    @GetMapping("toDelete")
    public ModelAndView toDelete(Long fileId) {
        ModelAndView mv = new ModelAndView();
        fileService.deleteFile(fileId);
        mv.setViewName("redirect:/admin/file");
        return mv;
    }

}
