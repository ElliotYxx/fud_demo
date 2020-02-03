package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface UserDao {
    User findByUsername(String username);
    String findRoles(String username);
    void createUser(User user);
    void updateUser(User user);
}