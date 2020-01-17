package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.UserDao;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public String findUsernameById(int id) {
        String username=userDao.findUsernameById(id);
        return username;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
       User user= userDao.findUserByUsernameAndPassword(username,password);
        return user;
    }
}
