package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


public interface SuperVIPService {
    public Boolean becomeAdmin(int user_id);
    public Boolean removeAdmin(int user_id);
    public int getUserRoles(int user_id);
    public List<User> getAllUserExceptSuperVIP();
    public List<Integer> getAllUserRoles();
    public void addUser(int user_id);
    public void removeUser(int user_id);
}
