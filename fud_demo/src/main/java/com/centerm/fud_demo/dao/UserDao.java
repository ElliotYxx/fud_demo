package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;

public interface UserDao {
    public String findUsernameById(int id);
    public User findUserByUsernameAndPassword(String username,String password);
}
