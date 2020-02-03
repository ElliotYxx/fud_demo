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
import java.util.List;

/**
 * 超级管理员控制类
 * @author jerry
 */
@Controller
@RequestMapping("supervip")
@Slf4j
public class SuperVipController {

    static final long USER = 1;
    static final long ADMIN = 2;

    @Autowired
    SuperVipService superVipService;

    @GetMapping("permission")
    @RequiresRoles(value = "SUPERVIP")
    public String permission(ServletRequest request)
    {
        List<User> userList=superVipService.getAllUserExceptSuperVIP();
        for (User user : userList) {
            if (user.getRoleId().equals(USER)) {
                user.setRole("user");
            }else{
                user.setRole("admin");
            }
        }
        request.setAttribute("userList",userList);
        return "supervip/permission";
    }

    @RequestMapping("/handleAdmin")
    @RequiresRoles(value = "SUPERVIP")
    public ModelAndView handleAdmin(ServletRequest request)
    {
        ModelAndView mv=new ModelAndView();
        Long userId=Long.parseLong(request.getParameter("id"));
        if (superVipService.getUserRoles(userId) == ADMIN)
        {
            superVipService.removeAdmin(userId);
        }else if (superVipService.getUserRoles(userId) == USER)
        {

            superVipService.becomeAdmin(userId);
        }
        DefaultWebSecurityManager securityManager=(DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm=(UserRealm)securityManager.getRealms().iterator().next();
        userRealm.clearAllCache();
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
        mv.setViewName("redirect:/supervip/permission");
        return mv;
    }

}
