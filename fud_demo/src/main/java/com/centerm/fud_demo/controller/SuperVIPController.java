package com.centerm.fud_demo.controller;

import com.alibaba.fastjson.serializer.MapSerializer;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.exception.SuperVipRemoveAdminException;
import com.centerm.fud_demo.service.SuperVIPService;
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
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superVIP")
@Slf4j
public class SuperVIPController {
    @Autowired
    private SuperVIPService superVIPService;
    @GetMapping("/toSuperVIP_permission")
    @RequiresRoles(value = "SUPERVIP")
    public String toSuperVip_permission(ServletRequest request)
    {
     List<User> userList=superVIPService.getAllUserExceptSuperVIP();
     List<Integer> roleList=superVIPService.getAllUserRoles();
     for (int i=0;i<userList.size();i++)
     {
         if (roleList.get(i).equals(1))
         {
             userList.get(i).setRole("user");
         }else if (roleList.get(i).equals(2))
         {
             userList.get(i).setRole("admin");
         }else {
             userList.get(i).setRole("superVIP");
         }
     }
      request.setAttribute("userList",userList);
        return "/superVIP/superVIP_permission";
    }

    @RequestMapping("/handleAdmin")
    @RequiresRoles(value = "SUPERVIP")
    public ModelAndView handleAdmin(ServletRequest request) throws Exception
    {
        ModelAndView mv=new ModelAndView();
        Integer user_id=Integer.parseInt(request.getParameter("id"));
        if (superVIPService.getUserRoles(user_id)==2)
        {
            //已经是管理员，注销管理员
            superVIPService.removeAdmin(user_id);
            superVIPService.addUser(user_id);
        }else if (superVIPService.getUserRoles(user_id)==1)
        {
            //还不是管理员，成为管理员
            superVIPService.removeUser(user_id);
            superVIPService.becomeAdmin(user_id);
        } else
        {
            throw new SuperVipRemoveAdminException();
        }
        DefaultWebSecurityManager securityManager=(DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm=(UserRealm)securityManager.getRealms().iterator().next();
        userRealm.clearAllCache();
        /*userRealm.getAuthorizationCache().remove(SecurityUtils.getSubject().getPrincipal());*/
        mv.setViewName("forward:/superVIP/toSuperVIP_permission");
        return mv;
    }

}
