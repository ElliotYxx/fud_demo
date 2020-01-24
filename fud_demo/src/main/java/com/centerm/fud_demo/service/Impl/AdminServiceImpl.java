package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.AdminDao;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public List<User> getAllUser() {
        List<User> userList=adminDao.getAllUser();
        return userList;
    }

    @Override
    public Integer getUserState(int user_id) {
      return   adminDao.getUserState(user_id);
    }

    @Override
    public Boolean banUser(int user_id) {
        return adminDao.banUser(user_id);
    }

    @Override
    public Boolean releaseUser(int user_id) {
        return adminDao.releaseUser(user_id);
    }

    @Override
    public List<User> getUserExceptAdminAndSuperVIP(int user_id) {
        return adminDao.getUserExceptAdminAndSuperVIP(user_id);
    }
}
