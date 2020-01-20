package com.centerm.fud_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("/getAllDownload")
    public ModelAndView getAllDownload()
    {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/admin_download");
        return mv;
    }
}
