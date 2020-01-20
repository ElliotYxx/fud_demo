package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdminDao {
    public List<User> getAllUser();
    public int getUserState(int user_id);
    public Boolean banUser(int user_id);
    public Boolean releaseUser(int user_id);
}
