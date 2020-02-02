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
import java.util.List;

/**
 * 用户控制类
 * @author jerry sheva
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
    @GetMapping("toUpload")
    public String toUploading()
    {
        return "user/upload";
    }
    @GetMapping("information")
    public String userInformation()
    {
        return "user/information";
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
        model.addAttribute("mostDownloaded", mostDownloaded);
        model.addAttribute("downloadTimesByCurrUser", downloadTimesByCurrUser);
        model.addAttribute("downloadTimes", downloadTimes);
        model.addAttribute("uploadTimes", uploadTimes);
        return "user/index";
    }

    @GetMapping("toLogin")
    public String toLogin()
    {
        return "login";
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
        if (exception == null){
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

        if (username ==null || password == null || username == "" || password == "") {
            throw new AuthenticationException();
        }
        if (!password.equals(rPassword))
        {
            throw new PasswordNotEqualsRetypePasswordException();
        }
        if (checkBox.equals("0"))
        {
            throw new NotAcceptTermsException();
        }
        User user=new User(username,password);
        User matching=userService.findByUsername(username);
        if (null == matching)
        {
            userService.createUser(user);
            Long userId=userService.findUserIdByUsername(username);
            userService.createUserRole(userId);
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
        String password = request.getParameter("password");
        AjaxReturnMsg msg=new AjaxReturnMsg();
        if (null == password || "".equals(password)){
            //TODO
            return null;
        }else{
            userService.changePassword(currUser.getUsername(), password);
            log.info("用户：" + currUser.getUsername() + " 修改密码成功...");
            msg.setUsername(currUser.getUsername());
            msg.setFlag(1);
        }
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
