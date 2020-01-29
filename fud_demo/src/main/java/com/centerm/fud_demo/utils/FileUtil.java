package com.centerm.fud_demo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件相关操作工具类（上传、下载)
 * @author sheva
 */
public class FileUtil {

    /**
     * 上传文件函数
     * @param file 文件字节码
     * @param filePath 文件路径
     * @param fileName 文件名
     * @throws Exception 异常
     */

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);

        if (!targetFile.exists()) {
            targetFile.mkdir();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);

        out.write(file);
        out.flush();
        out.close();
    }

    public static void backupFile(byte[] file, String filePath, String fileName) throws Exception{
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdir();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);

        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * fixme（需要在服务器上测试）
     */
    public static void downloadFile(com.centerm.fud_demo.entity.File downloadFile, String downloadPath,
                                    HttpServletRequest request, HttpServletResponse response) {
        // 设置文件名，根据业务需要替换成要下载的文件名
        String fileName = downloadFile.getName();
        if (fileName != null) {
            //设置文件路径
            java.io.File file = new File(downloadPath, fileName);
            if (file.exists()) {
                response.setContentType("application/octet-stream");
                response.setHeader("content-type", "application/octet-stream");
                // 设置文件名
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

