package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface UserDao {
    String findUsernameById(Long id);
    User findUserByUsernameAndPassword(String username,String password);
    User findByUsername(String username);
    Set<String> findRoles(String username);
    void createUser(User user);
    void updateUser(User user);
    void createUserRole(Long user_id);
    Long findUserIdByUsername(String username);
}
