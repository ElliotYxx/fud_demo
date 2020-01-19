package com.centerm.fud_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FudDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FudDemoApplication.class, args);
    }

}
