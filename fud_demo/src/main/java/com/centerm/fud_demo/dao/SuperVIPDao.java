package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 超级管理员映射
 * @author jerry
 */
@Component
public interface SuperVIPDao {
    Boolean becomeAdmin(int user_id);
    Boolean removeAdmin(int user_id);
    int getUserRoles(int user_id);
    List<User> getAllUserExceptSuperVIP();
    List<Integer> getAllUserRoles();
    void addUser(int user_id);
    void removeUser(int user_id);
}
