package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    String findUsernameById(int id);
    User findUserByUsernameAndPassword(String username,String password);
}
