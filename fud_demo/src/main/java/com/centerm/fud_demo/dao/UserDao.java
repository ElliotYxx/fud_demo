package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface UserDao {
    public String findUsernameById(int id);
    public User findUserByUsernameAndPassword(String username,String password);
    public User findByUsername(String username);
    public Set<String> findRoles(String username);
    public Set<String> findPermissions(String username);
    public void createUser(User user);
    public void updateUser(User user);
    public Boolean setUserOnline(int user_id);
    public Boolean setUserOffline(int user_id);
    public Boolean getUserOnlineState(String username);
}
