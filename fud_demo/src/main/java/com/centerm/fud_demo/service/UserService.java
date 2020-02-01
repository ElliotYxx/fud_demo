package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;


public interface UserService {
    String findUsernameById(Long id);
    User findUserByUsernameAndPassword(String username,String password);
    User findByUsername(String username);
    Set<String> findRoles(String username);
    void createUser(User user);
    void changePassword(String username,String password);
    void createUserRole(Long user_id);
    Long findUserIdByUsername(String username);
}
