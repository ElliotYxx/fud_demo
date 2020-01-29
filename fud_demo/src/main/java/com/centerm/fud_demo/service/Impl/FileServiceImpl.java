package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.File;
import com.centerm.fud_demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件相关操作实现类
 * @author sheva
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;
    @Override
    public List<File> getAllFileByUsername(String username) {
        return fileDao.getAllFileByUsername(username);
    }

    @Override
    public Boolean addFile(File file) {
        return fileDao.addFile(file);
    }

    @Override
    public File getFileById(Integer id) {
        return fileDao.getFileById(id);
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
