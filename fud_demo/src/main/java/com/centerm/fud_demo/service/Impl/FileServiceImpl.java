package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.File;
import com.centerm.fud_demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDao fileDao;
    @Override
    public List<File> getAllFileByUsername(String username) {
        return fileDao.getAllFileByUsername(username);
    }

    @Override
    public List<File> getAllFile() {
        return fileDao.getAllFile();
    }

    @Override
    public List<Integer> getDownloadNumbers() {
        return fileDao.getDownloadNumbers();
    }
}
