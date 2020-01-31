package com.centerm.fud_demo.controller;

import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.exception.MultiAccountOnlineException;
import com.centerm.fud_demo.exception.UsernameRepeatingException;
import com.centerm.fud_demo.listener.Listener;
import com.centerm.fud_demo.service.UserService;
import com.centerm.fud_demo.shiro.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
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
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

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

    @RequestMapping("/login")
    public String login(HttpServletRequest request)
    {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println(request.getSession().getServletContext().getSessionTimeout());
        User user=new User(username,password);
        if (username==null||password==null)
        {
            return "login";
        }
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());

            if (!subject.isAuthenticated()) {
                subject.login(token);
            }

        String exception=(String)request.getAttribute("shiroLoginFailure");
        if (exception==null){
            log.info("用户 " + username + " 登录成功");
            User to_index=userService.findByUsername(username);
            request.getSession().setAttribute("user", to_index);
           return "logged/user_index";
        }else
        {
            return "login";
        }
    }
    @RequestMapping("/register")
    public String register(ServletRequest request)throws Exception
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username==null||password==null) {
            return "register";
        }
        User user=new User(username,password);
        User matching=userService.findByUsername(username);
        if (matching==null)
        {
            userService.createUser(user);
            int user_id=userService.findUserIdByUsername(username);
            userService.createUserRole(user_id);
            log.info("用户 "+username+" 注册成功"+",默认权限为user");

        }else
        {
            throw new UsernameRepeatingException();
        }
       return "login";
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
