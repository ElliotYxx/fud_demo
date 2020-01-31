package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService {
    List<User> getAllUser();
    Integer getUserState(int user_id);
    Boolean banUser(int user_id);
    Boolean releaseUser(int user_id);
    List<User> getUserExceptAdminAndSuperVIP(int user_id);
}
