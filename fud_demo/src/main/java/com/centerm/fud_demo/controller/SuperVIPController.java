package com.centerm.fud_demo.controller;

import com.centerm.fud_demo.exception.SuperVipRemoveAdminException;
import com.centerm.fud_demo.service.SuperVIPService;
import com.centerm.fud_demo.shiro.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;

@Controller
@RequestMapping("/superVIP")
public class SuperVIPController {
    @Autowired
    private SuperVIPService superVIPService;
    @GetMapping("/toSuperVIP_permission")

    @RequestMapping("/handleAdmin")
    public ModelAndView handleAdmin(ServletRequest request) throws Exception
    {
        ModelAndView mv=new ModelAndView();
        Integer user_id=Integer.parseInt(request.getParameter("id"));
        if (superVIPService.getUserRoles(user_id)==2)
        {
            //已经是管理员，注销管理员
            superVIPService.removeAdmin(user_id);
            DefaultWebSecurityManager securityManager=(DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
            UserRealm userRealm=(UserRealm)securityManager.getRealms().iterator().next();
            userRealm.clearAllCache();
        }else if (superVIPService.getUserRoles(user_id)==1)
        {
            //还不是管理员，成为管理员
            superVIPService.becomeAdmin(user_id);
            DefaultWebSecurityManager securityManager=(DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
            UserRealm userRealm=(UserRealm)securityManager.getRealms().iterator().next();
            userRealm.clearAllCache();
        } else
        {
            throw new SuperVipRemoveAdminException();
        }
        mv.setViewName("redirect :/superVIP/toSuperVIP_permission");
        return mv;
    }

}
