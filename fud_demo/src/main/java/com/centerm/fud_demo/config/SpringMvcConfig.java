package com.centerm.fud_demo.config;

import com.centerm.fud_demo.listener.Listener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Bean
    public ServletListenerRegistrationBean getListen()
    {
        ServletListenerRegistrationBean servletListenerRegistrationBean=new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new Listener());
        return servletListenerRegistrationBean;
    }
}
