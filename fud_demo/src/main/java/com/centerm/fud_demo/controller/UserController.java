package com.centerm.fud_demo.controller;

import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/toDownload")
    public String toDownload()
    {
        return "user/download";
    }
    @GetMapping("/toUploading")
    public String toUploading()
    {
        return "user/uploading";
    }
    @GetMapping("/toUser_information")
    public String toUser_information()
    {
        return "user/toUser_information";
    }
    @GetMapping("toUser_index")
    public String toUser_index()
    {
        return "user/user_index";
    }
    @GetMapping("/toUser_edit")
    public String toUser_edit()
    {
        return "user/user_edit";
    }
    @GetMapping("/toLogin")
    public String toLogin()
    {
        return "login";
    }
    @PostMapping("/login")
    public ModelAndView login(ServletRequest request)
    {
        ModelAndView mv=new ModelAndView();
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        User user=userService.findUserByUsernameAndPassword(username,password);
        if (user==null)
        {
            log.warn("用户名或者密码错误，登录失败");
            mv.setViewName("redirect:/toLogin");
            return mv;
        }
        log.info("用户名"+username+"登录成功");
        mv.setViewName("user/user_index");
       request.setAttribute("user",user);
        return mv;
    }
}
