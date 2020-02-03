package com.centerm.fud_demo.controller;

import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.ajax.AjaxReturnMsg;
import com.centerm.fud_demo.exception.NotAcceptTermsException;
import com.centerm.fud_demo.exception.PasswordNotEqualsRetypePasswordException;
import com.centerm.fud_demo.exception.UsernameRepeatingException;
import com.centerm.fud_demo.service.DownloadService;
import com.centerm.fud_demo.service.FileService;
import com.centerm.fud_demo.service.UploadService;
import com.centerm.fud_demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 用户控制类
 * @author jerry
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    User currUser = null;
    @Autowired
    UserService userService;
    @Autowired
    DownloadService downloadService;
    @Autowired
    UploadService uploadService;
    @Autowired
    FileService fileService;

    @GetMapping("toRegister")
    public String toRegister(){return "register";}
    @GetMapping("toUpload")
    public String toUploading()
    {
        return "user/upload1";
    }
    @GetMapping("information")
    public String userInformation()
    {
        return "user/information";
    }
    @GetMapping("toLogin")
    public String toLogin()
    {
        return "login";
    }
    @GetMapping("filemanager")
    public String userFileManager(Model model)
    {
        List<FileRecord> fileRecordList = fileService.getFileByUserId(currUser.getId());
        model.addAttribute("fileList", fileRecordList);
        return "user/filemanager";
    }

//    @GetMapping("hotfile")
//    public String userHotFile()
//    {
//        return "user/hotfile";
//    }

    @GetMapping("download")
    public String userDownload(Model model)
    {
        List<FileRecord> fileRecordList = fileService.getFileByUserId(currUser.getId());
        model.addAttribute("fileList", fileRecordList);
        return "user/download";
    }

    @GetMapping("index")
    public String userIndex(Model model, HttpServletRequest request)
    {
        currUser = (User)request.getSession().getAttribute("user");
        Long currUserId = currUser.getId();
        List<FileRecord> mostDownloaded = downloadService.getMostDownloadRecord();
        Long downloadTimes = downloadService.getDownloadTimes();
        Long downloadTimesByCurrUser = downloadService.getDownloadTimesByUserId(currUserId);
        Long uploadTimes = uploadService.getUploadTimes();
        List<FileRecord> latestDownloaded = downloadService.getLatestDownloaded();
        List<FileRecord> latestUploaded = uploadService.getLatestUploaded();
        model.addAttribute("mostDownloaded", mostDownloaded);
        model.addAttribute("downloadTimesByCurrUser", downloadTimesByCurrUser);
        model.addAttribute("downloadTimes", downloadTimes);
        model.addAttribute("uploadTimes", uploadTimes);
        model.addAttribute("latestDownloaded", latestDownloaded);
        model.addAttribute("latestUploaded", latestUploaded);
        return "user/index";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public AjaxReturnMsg login(HttpServletRequest request)
    {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        AjaxReturnMsg msg=new AjaxReturnMsg();
        User user=new User(username,password);
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());

        if (!subject.isAuthenticated()) {
            subject.login(token);
        }
        String exception=(String)request.getAttribute("shiroLoginFailure");
        if (null == exception){
            log.info("用户 " + username + " 登录成功");
            User to_index=userService.findByUsername(username);
            request.getSession().setAttribute("user", to_index);
            request.getSession().setAttribute("index",username.substring(0,1).toUpperCase());
            msg.setFlag(1);
            msg.setUsername(username);
        }
        return msg;
    }
    @PostMapping("register")
    @ResponseBody
    public AjaxReturnMsg register(HttpServletRequest request)throws Exception
    {
        AjaxReturnMsg msg=new AjaxReturnMsg();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rPassword =request.getParameter("r_password");
        String checkBox=request.getParameter("check");

        if (null == username || null == password || "" == username || "" == password) {
            throw new AuthenticationException();
        }
        if (!password.equals(rPassword))
        {
            throw new PasswordNotEqualsRetypePasswordException();
        }
        if (("0").equals(checkBox))
        {
            throw new NotAcceptTermsException();
        }
        User user=new User(username,password);
        User matching=userService.findByUsername(username);
        if (null == matching)
        {
            userService.createUser(user);
            log.info("用户 "+username+" 注册成功"+",默认权限为user");
            msg.setUsername(username);
            msg.setFlag(1);
        }else
        {
            throw new UsernameRepeatingException();
        }
        return msg;
    }

    @PostMapping("information")
    @ResponseBody
    public AjaxReturnMsg updateUser(HttpServletRequest request){
        AjaxReturnMsg msg=new AjaxReturnMsg();
        String password=request.getParameter("password");
        String username=((User)request.getSession().getAttribute("user")).getUsername();
        if ((password.equals(null)||password.equals("")))
        {
            msg.setFlag(0);
            msg.setMsg("没有提交数据更新");
            return msg;
        }
        if (!(password.equals(null)||password.equals("")))
        {
            userService.changePassword(username,password);
        }
        User user=userService.findByUsername(username);
        request.getSession().setAttribute("user",user);
        msg.setFlag(1);
        msg.setMsg("数据更新成功");
        return msg;
    }

    @RequestMapping("logout")
    @ResponseBody
    public ModelAndView logout(HttpServletRequest request)
    {
        Subject subject= SecurityUtils.getSubject();
        log.info("用户 "+((User)request.getSession().getAttribute("user")).getUsername()+" 登出成功");
        subject.logout();
        ModelAndView mv=new ModelAndView();
        mv.setViewName("login");
        return mv;
    }


}
