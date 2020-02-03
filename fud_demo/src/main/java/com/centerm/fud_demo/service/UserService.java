package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;

public interface UserService {
    User findByUsername(String username);
    String findRoles(String username);
    void createUser(User user);
    void changePassword(String username,String password);

}