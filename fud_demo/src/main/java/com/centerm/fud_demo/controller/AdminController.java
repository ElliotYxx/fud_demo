package com.centerm.fud_demo.controller;

import com.centerm.fud_demo.entity.File;
import com.centerm.fud_demo.entity.Permission;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.exception.AccountBanException;
import com.centerm.fud_demo.service.AdminService;
import com.centerm.fud_demo.service.FileService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
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

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private FileService fileService;
    @GetMapping("/toAdmin_download")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String toAdmin_download(ServletRequest request)
    {
        List<File> fileList=fileService.getAllFile();
        request.setAttribute("fileList",fileList);
        return "admin/admin_download";
    }
    @GetMapping("/toAdmin_index")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String toAdmin_index()
    {
        return "admin/admin_index";
    }
    @GetMapping("/toAdmin_userView")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String toAdmin_userView()
    {
        return "admin/admin_userView";
    }
    @GetMapping("/toAdmin_ban")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String toAdmin_ban(ServletRequest request) {
      List<User> userList = adminService.getAllUser();
      request.setAttribute("userList",userList);
        return "admin/admin_ban";
    }
    @RequestMapping("/banUser")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String banUser(HttpServletRequest request)throws Exception
    {
        User target=(User) request.getSession().getAttribute("user");
       Integer user_state = target.getState();
       Integer user_id=target.getId();
       if(user_state.equals(0))
       {
           //执行账号封禁
           Boolean is_success= adminService.banUser(user_id);
           if(is_success.equals(0))
           {
               //账号封禁失败
               throw new AccountBanException();
           }
       }else {
           //执行账号解锁
           Boolean is_success = adminService.releaseUser(user_id);
           if(is_success.equals(0))
           {
               //账号解锁失败
               throw new AccountBanException();
           }
       }
      return "admin/toAdmin_ban";
    }

}
