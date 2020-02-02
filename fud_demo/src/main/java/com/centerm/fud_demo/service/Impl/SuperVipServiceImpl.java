package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.SuperVipDao;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.SuperVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 超级管理员实现类
 * @author sheva
 */
@Service
public class SuperVipServiceImpl implements SuperVipService {
    @Autowired
    private SuperVipDao superVipDao;
    @Override
    public Boolean becomeAdmin(Long userId) {
        return superVipDao.becomeAdmin(userId);
    }

    @Override
    public Boolean removeAdmin(Long userId) {
        return superVipDao.removeAdmin(userId);
    }

    @Override
    public int getUserRoles(Long userId) {
        return superVipDao.getUserRoles(userId);
    }

    @Override
    public List<User> getAllUserExceptSuperVIP() {
        return superVipDao.getAllUserExceptSuperVIP();
    }
    @Override
    public List<Integer> getAllUserRoles() {
        return superVipDao.getAllUserRoles();
    }

    @Override
    public void addUser(Long userId) {
        superVipDao.addUser(userId);
    }

    @Override
    public void removeUser(Long userId) {
        superVipDao.removeUser(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        superVipDao.deleteUser(userId);
    }
}
