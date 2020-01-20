package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface UserService {
    String findUsernameById(int id);
    User findUserByUsernameAndPassword(String username,String password);
    public User findByUsername(String username);
    public Set<String> findRoles(String username);
    public Set<String> findPermissions(String username);
    public void createUser(User user);
    public void changePassword(String username,String password);
    public Boolean setUserOnline(int user_id);
    public Boolean setUserOffline(int user_id);
    public Boolean getUserOnlineState(String username);
}
