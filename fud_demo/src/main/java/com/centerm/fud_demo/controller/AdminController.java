package com.centerm.fud_demo.controller;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.exception.AccountBanException;
import com.centerm.fud_demo.listener.Listener;
import com.centerm.fud_demo.service.AdminService;
import com.centerm.fud_demo.service.FileService;
import com.centerm.fud_demo.service.UserService;
import com.centerm.fud_demo.shiro.UserRealm;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @GetMapping("/toAdmin_download")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String toAdmin_download(ServletRequest request)
    {
        List<FileRecord> fileList=fileService.getAllFile();
        request.setAttribute("fileList",fileList);
        return "admin/admin_download";
    }
    @GetMapping("/toAdmin_index")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String toAdmin_index(ServletRequest request)
    {
       AtomicInteger userNum=Listener.sessionCount;
        request.setAttribute("userNum",userNum);
        return "admin/admin_index";
    }
    @GetMapping("/toAdmin_userView")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String toAdmin_userView(ServletRequest request)
    {
        request.setAttribute("userNum", Listener.sessionCount);
        return "admin/admin_userView";
    }
    @GetMapping("/toAdmin_ban")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String toAdmin_ban(HttpServletRequest request) {
        User user=(User)request.getSession().getAttribute("user");
        int user_id=user.getId();
        List<User> userList = adminService.getUserExceptAdminAndSuperVIP(user_id);
        request.setAttribute("userList",userList);
        return "admin/admin_ban";
    }
    @RequestMapping("/banUser")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public ModelAndView banUser(HttpServletRequest request)throws AccountBanException
    {
        ModelAndView mv=new ModelAndView();
        String username=request.getParameter("username");
        User target=userService.findByUsername(username);
       Integer user_state = target.getState();
       Integer user_id=target.getId();
       if(user_state.equals(0))
       {
           //执行账号封禁
           Boolean is_success= adminService.banUser(user_id);
           if (is_success.equals(0))
           {
               throw new AccountBanException();
           }
           log.info("用户 "+username+"　被封禁");
       }else {
           //执行账号解锁
           Boolean is_success = adminService.releaseUser(user_id);
           if (is_success.equals(0))
           {
               throw new AccountBanException();
           }
           log.info("用户 "+username+"　被解除封禁");
       }
        DefaultWebSecurityManager securityManager;
        securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm shiroRealm = (UserRealm) securityManager.getRealms().iterator().next();
        shiroRealm.clearAllCache();
     mv.setViewName("redirect:/admin/toAdmin_ban");
       return mv;
    }

}
