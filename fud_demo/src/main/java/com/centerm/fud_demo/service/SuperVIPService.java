package com.centerm.fud_demo.service;

import org.springframework.stereotype.Component;


public interface SuperVIPService {
    public Boolean becomeAdmin(int user_id);
    public Boolean removeAdmin(int user_id);
    public int getUserRoles(int user_id);
}
