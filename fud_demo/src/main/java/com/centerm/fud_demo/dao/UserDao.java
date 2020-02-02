package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface UserDao {
    /**
     * 根据id找用户名
     * @param id
     * @return
     */
    String findUsernameById(Long id);

    /**
     * 根据用户名和密码找用户
     * @param username
     * @param password
     * @return
     */
    User findUserByUsernameAndPassword(String username,String password);

    /**
     * 根据用户名找用户
     * @param username  用户名
     * @return 用户
     */
    User findByUsername(String username);

    /**
     * 根据用户名找用户角色
     * @param username 返回用户角色
     * @return 用户角色实体类
     */
    Set <String> findRoles(String username);

    /**
     * 创造用户
     * @param user 用户实体类
     */
    void createUser(User user);

    /**
     * 更新用户
     * @param user 用户实体类
     */
    void updateUser(User user);

    /**
     * 创造用户角色
     * @param userId 用户id
     */
    void createUserRole(Long userId);

    /**
     * 根据用户名找用户id
     * @param username 用户名
     * @return 用户id
     */
    Long findUserIdByUsername(String username);
}
