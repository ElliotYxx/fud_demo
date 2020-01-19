package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.File;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface FileService {
    List<File> getAllFileByUsername(String username);
}
