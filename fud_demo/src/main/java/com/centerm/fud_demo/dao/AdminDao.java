package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdminDao {
    List<User> getAllUser();
    int getUserState(int user_id);
    Boolean banUser(int user_id);
    Boolean releaseUser(int user_id);
    List<User> getUserExceptAdminAndSuperVIP(int user_id);
}
