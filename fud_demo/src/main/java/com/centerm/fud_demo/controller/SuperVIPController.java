package com.centerm.fud_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;

@Controller
@RequestMapping("/superVIP")
public class SuperVIPController {
    @RequestMapping("/handleVIP")
    public String removeVIP(ServletRequest request)
    {

    }

}
