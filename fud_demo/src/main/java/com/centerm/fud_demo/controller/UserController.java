package com.centerm.fud_demo.controller;

import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.exception.MultiAccountOnlineException;
import com.centerm.fud_demo.exception.UsernameRepeatingException;
import com.centerm.fud_demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/toDownload")
    public String toDownload()
    {
        return "logged/user_download";
    }
    @GetMapping("/toRegister")
    public String toRegister(){return "register";}
    @GetMapping("/toUploading")
    public String toUploading()
    {
        return "logged/user_uploading";
    }
    @GetMapping("/toUser_information")
    public String toUser_information()
    {
        return "logged/user_information";
    }
    @GetMapping("/toUser_index")
    public String toUser_index()
    {
        return "logged/user_index";
    }
    @GetMapping("/toUser_edit")
    public String toUser_edit()
    {
        return "logged/user_edit";
    }
    @GetMapping("/toLogin")
    public String toLogin()
    {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request)throws Exception
    {
        HttpSession httpSession=request.getSession(true);
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        User user=new User(username,password);
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        if(!subject.isAuthenticated())
        {
            subject.login(token);
        }
        if(userService.getUserOnlineState(user.getId()).equals(1))
        {
            throw new MultiAccountOnlineException();
        }
        String exception=(String)request.getAttribute("shiroLoginFailure");
        if (exception!=null)
        {
            log.warn("用户 "+username+" 用户名或者密码错误，登录失败");
            return "login";
        }else {
            log.info("用户名 " + username + " 登录成功");
            User to_index=userService.findByUsername(username);
            httpSession.setAttribute(to_index.getUsername(),to_index.getUsername());
            userService.setUserOnline(to_index.getId());
            request.getSession().setAttribute("user", to_index);
           return "logged/user_index";
        }
    }
    @PostMapping("/register")
    public ModelAndView register(ServletRequest request)throws Exception
    {
        ModelAndView mv=new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username.equals("") || password.equals("")) {
            mv.setViewName("register");
        }
        User user=new User(username,password);
        User matching=userService.findByUsername(username);
        if (matching==null)
        {
            userService.createUser(user);
            log.info("用户 "+username+" 注册成功");
            mv.setViewName("login");
        }else
        {
            throw new UsernameRepeatingException();
        }
        return mv;
    }
    @PostMapping("edit")
    public ModelAndView edit(ServletRequest request)
    {   userService.changePassword(request.getParameter("username"),request.getParameter("password"));
        ModelAndView mv=new ModelAndView();
        mv.setViewName("logged/user_index");
        return mv;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public ModelAndView logout(HttpServletRequest request,User user)
    {
        HttpSession httpSession=request.getSession(true);
        httpSession.removeAttribute(user.getUsername());
        httpSession.invalidate();
        userService.setUserOffline(user.getId());
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
        ModelAndView mv=new ModelAndView();
        mv.setViewName("login");
        return mv;
    }
}
