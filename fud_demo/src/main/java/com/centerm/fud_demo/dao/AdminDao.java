package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 管理员映射
 * @author jerry
 */
@Component
public interface AdminDao {
    List<User> getAllUser();
    int getUserState(Long user_id);
    Boolean banUser(Long user_id);
    Boolean releaseUser(Long user_id);
    List<User> getUserExceptAdminAndSuperVIP(Long user_id);
}
