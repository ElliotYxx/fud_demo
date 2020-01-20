package com.centerm.fud_demo.dao;

import org.springframework.stereotype.Component;

@Component
public interface SuperVIPDao {
    public Boolean becomeAdmin(int user_id);
    public Boolean removeAdmin(int user_id);
}
