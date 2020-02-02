package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * 用户服务类
 * @author jerry
 */
public interface UserService {
    /**
     *根据用户id找用户名
     * @param id 用户id
     * @return
     */
    String findUsernameById(Long id);

    /**
     * 根据用户名和密码找用户
     * @param username 用户名
     * @param password  密码
     * @return 用户实体类
     */
    User findUserByUsernameAndPassword(String username,String password);

    /**
     * 根据用户名找用户
     * @param username  用户名
     * @return 用户实体类
     */
    User findByUsername(String username);

    /**
     * 根据用户名找用户角色
     * @param username 用户名
     * @return 角色集合
     */
    Set<String> findRoles(String username);

    /**
     * 创建用户
     * @param user 用户实体类
     */
    void createUser(User user);

    /**
     * 修改密码
     * @param username 用户名
     * @param password  密码
     */
    void changePassword(String username,String password);

    /**
     * 创造用户角色
     * @param userId 用户id
     */
    void createUserRole(Long userId);

    /**
     * 根据用户名查询用户id
     * @param username 用户名
     * @return
     */
    Long findUserIdByUsername(String username);
}
