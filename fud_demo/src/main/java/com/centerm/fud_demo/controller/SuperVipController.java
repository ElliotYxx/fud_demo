package com.centerm.fud_demo.controller;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.SuperVipService;
import com.centerm.fud_demo.shiro.UserRealm;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
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

/**
 * 超级管理员控制类
 * @author jerry
 */
@Controller
@RequestMapping("supervip")
@Slf4j
public class SuperVipController {

    static final int USER = 1;
    static final int ADMIN = 2;

    @Autowired
    SuperVipService superVipService;

    @GetMapping("permission")
    @RequiresRoles(value = "SUPERVIP")
    public String permission(ServletRequest request)
    {
        List<User> userList=superVipService.getAllUserExceptSuperVIP();
        List<Integer> roleList=superVipService.getAllUserRoles();
        for (int i = 0; i < userList.size(); i++)
        {
            if (roleList.get(i).equals(USER))
            {
                userList.get(i).setRole("user");
            }else if (roleList.get(i).equals(ADMIN))
            {
                userList.get(i).setRole("admin");
            }else {
                userList.get(i).setRole("superVIP");
            }
        }
        request.setAttribute("userList",userList);
        return "supervip/permission";
    }

    @RequestMapping("/handleAdmin")
    @RequiresRoles(value = "SUPERVIP")
    public ModelAndView handleAdmin(ServletRequest request) throws Exception
    {
        ModelAndView mv=new ModelAndView();
        Long userId=Long.parseLong(request.getParameter("id"));
        if (superVipService.getUserRoles(userId) == ADMIN)
        {
            //已经是管理员，注销管理员
            superVipService.removeAdmin(userId);
            superVipService.addUser(userId);
        }else if (superVipService.getUserRoles(userId) == USER)
        {
            //还不是管理员，成为管理员
            superVipService.removeUser(userId);
            superVipService.becomeAdmin(userId);
        }
        DefaultWebSecurityManager securityManager=(DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm=(UserRealm)securityManager.getRealms().iterator().next();
        userRealm.clearAllCache();
        /*userRealm.getAuthorizationCache().remove(SecurityUtils.getSubject().getPrincipal());*/
        mv.setViewName("forward:/supervip/permission");
        return mv;
    }

    /**
     * @param userId 用户id
     * @return
     */
    @ApiOperation("删除用户")
    @GetMapping("delete")
    public ModelAndView deleteUser(Long userId) {
        ModelAndView mv=new ModelAndView();
        superVipService.removeUser(userId);
        superVipService.deleteUser(userId);
        mv.setViewName("redirect:/supervip/permission");
        return mv;
    }

}
