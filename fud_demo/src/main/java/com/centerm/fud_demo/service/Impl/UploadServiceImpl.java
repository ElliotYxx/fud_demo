package com.centerm.fud_demo.service.Impl;
import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.BackupRecord;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.service.BackupService;
import com.centerm.fud_demo.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/30 下午2:20
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Value("${filePath}")
    private String uploadPath;
    @Value("${backupPath}")
    private String backupPath;
    private Long userId = null;
    @Autowired
    private FileDao fileDao;
    @Override
    public Long getUploadTimes() {
        return fileDao.getUploadTimes();
    }

    @Override
    public List<FileRecord> getLatestUploaded() {
        return fileDao.getLatestUploaded();
    }

    @Override
    public void upload(MultipartFile file, Integer chunk, String guid, Long uploaderId){
        log.info("上传分片中...");
        String filePath = uploadPath + "temp" + File.separator + guid;
        File tempFile = new File(filePath);
        userId = uploaderId;
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        RandomAccessFile raFile = null;
        BufferedInputStream inputStream = null;
        if (null == chunk) {
            chunk = 0;
        }
        try {
            File dirFile = new File(filePath, String.valueOf(chunk));
            //以读写的方式打开目标文件
            raFile = new RandomAccessFile(dirFile, "rw");
            raFile.seek(raFile.length());
            inputStream = new BufferedInputStream(file.getInputStream());
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buf)) != -1) {
                raFile.write(buf, 0, length);
            }
        } catch (Exception e) {
            log.error("(upload)Exception: " + e.getMessage());
        } finally {
            if (null != inputStream) {
                try{
                    inputStream.close();
                }catch (Exception e){
                    log.error("(upload)inputStream: " + e.getMessage());
                }
            }
            if (null != raFile) {
                try{
                    raFile.close();
                }catch (Exception e){
                    log.error("(upload)raFile: " + e.getMessage());
                }
            }
        }
    }
    @Override
    public void combineBlock(String guid, String fileName) {
        //分片文件临时目录
        File tempPath = new File(uploadPath + "temp" + File.separator + guid);
        //真实上传路径
        String filePath = uploadPath + "real";
        File realPath = new File(filePath);
        if (!realPath.exists()) {
            realPath.mkdir();
        }
        File realFile = new File(uploadPath + "real" + File.separator + fileName);
        // 文件追加写入
        FileOutputStream os = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            log.info("combine block start...");
            log.info("file name is " + fileName + ", MD5: " + guid);
            os = new FileOutputStream(realFile, true);
            fcout = os.getChannel();
            if (tempPath.exists()) {
                //获取临时目录下的所有文件
                File[] tempFiles = tempPath.listFiles();
                //按名称排序
                Arrays.sort(tempFiles, (o1, o2) -> {
                    if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                        return -1;
                    }
                    if (Integer.parseInt(o1.getName()) == Integer.parseInt(o2.getName())) {
                        return 0;
                    }
                    return 1;
                });
                //每次读取10MB大小，字节读取
                //byte[] byt = new byte[10 * 1024 * 1024];
                //int len;
                //设置缓冲区为10MB
                ByteBuffer buffer = ByteBuffer.allocate(10 * 1024 * 1024);
                for (int i = 0; i < tempFiles.length; i++) {
                    FileInputStream fis = new FileInputStream(tempFiles[i]);
                    fcin = fis.getChannel();
                    if (fcin.read(buffer) != -1) {
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            fcout.write(buffer);
                        }
                    }
                    buffer.clear();
                    fis.close();
                    //删除分片
                    tempFiles[i].delete();
                }
                os.close();
                //删除临时目录
                if (tempPath.isDirectory() && tempPath.exists()) {
                    // 回收资源
                    System.gc();
                    tempPath.delete();
                }
                log.info("combine finished...");
                log.info("file name is " + fileName + ", MD5: " + guid);
                FileRecord fileRecord = new FileRecord(fileName, uploadPath + "real/" + fileName,
                        getFormatSize(realFile.length()), userId, guid, fileName.substring(fileName.lastIndexOf(".")));
                fileDao.addFile(fileRecord);
                //备份
                backupFile(filePath + File.separator, backupPath, fileName);
            }
        } catch (Exception e) {
            log.error("combine failed...");
            log.error(e.getMessage());
        }
    }



    @Override
    public void checkMd5(HttpServletRequest request, HttpServletResponse response) {
        //当前分片
        String chunk = request.getParameter("chunk");
        //分片大小
        String chunkSize = request.getParameter("chunkSize");
        //当前文件的MD5值
        String guid = request.getParameter("guid");
        //分片上传路径
        String tempPath = uploadPath + "temp";
        File checkFile = new File(tempPath + File.separator + guid + File.separator + chunk);
        response.setContentType("text/html;charset=utf-8");
        try {
            //如果当前分片存在，并且长度等于上传的大小
            if (checkFile.exists() && checkFile.length() == Integer.parseInt(chunkSize)) {
                response.getWriter().write("{\"ifExist\":1}");
            } else {
                response.getWriter().write("{\"ifExist\":0}");
            }
        } catch (IOException e) {
            log.error("(checkMD5)IOException: " + e.getMessage());
        }
    }

    public void backupFile(String copyFrom, String copyTo, String fileName) {
        log.info("backup start...");
        long start = System.currentTimeMillis();
        File source = new File(copyFrom + fileName);
        File target = new File(copyTo + fileName);
        File targetFolder = new File(copyTo);
        FileInputStream in = null;
        FileOutputStream out = null;
        if (!source.exists() || !source.isFile()){
            log.error("source doesn't exists or source isn't a file...");
        }
        if (!targetFolder.exists()){
            targetFolder.mkdirs();
        }
        try{
            target.createNewFile();
            in = new FileInputStream(source);
            out = new FileOutputStream(target);
            FileChannel inChannel = in.getChannel();
            WritableByteChannel outChannel = out.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();
            outChannel.close();
            in.close();
            out.close();
        }catch (FileNotFoundException e){
            log.error("File not found...");
        }catch (IOException e){
            log.error(e.getMessage());
        }
        log.info("backup finished...");
        Long fileId = fileDao.getFileIdByFileName(fileName);
        BackupRecord backupRecord = new BackupRecord(fileId, fileName, copyTo + fileName, userId);
        fileDao.addBackupRecord(backupRecord);
        long end = System.currentTimeMillis();
        log.info("backup lasts：" + (end-start) + "ms");
    }

    public String getFormatSize(double size){
        double kiloByte = size / 1024;
        if (kiloByte < 1){
            return size + "Byte(s)";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1){
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte/1024;
        if (gigaByte < 1){
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1){
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
