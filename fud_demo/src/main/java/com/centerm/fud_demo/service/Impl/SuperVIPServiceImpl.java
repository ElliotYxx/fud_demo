package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.SuperVIPDao;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.SuperVIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SuperVIPServiceImpl implements SuperVIPService {
    @Autowired
    private SuperVIPDao superVIPDao;
    @Override
    public Boolean becomeAdmin(int user_id) {
        return superVIPDao.becomeAdmin(user_id);
    }

    @Override
    public Boolean removeAdmin(int user_id) {
        return superVIPDao.removeAdmin(user_id);
    }

    @Override
    public int getUserRoles(int user_id) {
        return superVIPDao.getUserRoles(user_id);
    }

    @Override
    public List<User> getAllUserExceptSuperVIP() {
        return superVIPDao.getAllUserExceptSuperVIP();
    }
    @Override
    public List<Integer> getAllUserRoles() {
        return superVIPDao.getAllUserRoles();
    }

    @Override
    public void addUser(int user_id) {
        superVIPDao.addUser(user_id);
    }

    @Override
    public void removeUser(int user_id) {
        superVIPDao.removeUser(user_id);
    }
}
