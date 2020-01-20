package com.centerm.fud_demo.shiro;

import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
       String username=(String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.findRoles(username));
        authorizationInfo.setStringPermissions(userService.findPermissions(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
       String username=(String)token.getPrincipal();
        User user=userService.findByUsername(username);
        try {
            log.info(user.getUsername() + " " + user.getPassword());
        }catch (NullPointerException e)
        {}
        if(user==null)
        {
            throw new UnknownAccountException();
        }
        if (user.getState().equals(1))
        {
            throw new LockedAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), ByteSource.Util.bytes(user.getUsername()),getName());
        return authenticationInfo;
    }
}
