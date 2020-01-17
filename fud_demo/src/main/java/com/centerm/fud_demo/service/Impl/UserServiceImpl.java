package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.UserDao;
import com.centerm.fud_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public String findUsernameById(int id) {
        return null;
    }
}
