package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdminService {
    public List<User> getAllUser();
    public Integer getUserState(int user_id);
    public Boolean banUser(int user_id);
    public Boolean releaseUser(int user_id);
    public List<User> getOnlineUser();
}
