package com.centerm.fud_demo.controller;

import com.centerm.fud_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/toDownload")
    public String toDownload()
    {
        return "/user/download";
    }
    @GetMapping("/toUploading")
    public String toUploading()
    {
        return "/user/uploading";
    }
    @GetMapping("/toUser_information")
    public String toUser_information()
    {
        return "/user/toUser_information";
    }
    @GetMapping("toUser_index")
    public String toUser_index()
    {
        return "/user/user_index";
    }
    @GetMapping("/toUser_edit")
    public String toUser_edit()
    {
        return "/user/user_edit";
    }
}
