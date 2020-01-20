package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.UserDao;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.UserService;
import com.centerm.fud_demo.utils.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
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

    @Override
    public User findByUsername(String username) {
        User user=userDao.findByUsername(username);
        return user;
    }

    @Override
    public Set<String> findRoles(String username) {
        return userDao.findRoles(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        return userDao.findPermissions(username);
    }

    @Override
    public void createUser(User user) {
        PasswordHelper.encryptPassword(user);
        userDao.createUser(user);
    }

    @Override
    public void changePassword(String username, String password) {
        User user=userDao.findByUsername(username);
        user.setPassword(password);
        PasswordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }

    @Override
    public Boolean setUserOnline(int user_id) {
        return userDao.setUserOnline(user_id);
    }

    @Override
    public Boolean setUserOffline(int user_id) {
        return userDao.setUserOffline(user_id);
    }

    @Override
    public Boolean getUserOnlineState(String username) {
        return userDao.getUserOnlineState(username);
    }
}
