package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;


public interface UserService {
    String findUsernameById(int id);
    User findUserByUsernameAndPassword(String username,String password);
    User findByUsername(String username);
    Set<String> findRoles(String username);
    Set<String> findPermissions(String username);
    void createUser(User user);
    void changePassword(String username,String password);
    void createUserRole(int user_id);
    int findUserIdByUsername(String username);
}
