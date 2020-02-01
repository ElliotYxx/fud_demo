package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService {
    List<User> getAllUser();
    Integer getUserState(Long user_id);
    Boolean banUser(Long user_id);
    Boolean releaseUser(Long user_id);
    List<User> getUserExceptAdminAndSuperVIP(Long user_id);
}
