package com.centerm.fud_demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/20 上午11:24
 * 文件校验工具类
 */
public class VerificationUtil {

    /**
     * 默认的密码字符串组合，用来将字节码转换成16进制表示的字符，apache校验下载的文件的正确性就是有这个默认的组合
     */
    public static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};
    public static MessageDigest messageDigest = null;
    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(VerificationUtil.class.getName()
                    + "初始化失败，MessageDigest不支持MD5Util。");
            nsaex.printStackTrace();
        }
    }

    /**
     * 生成字符串的md5校验值
     */
    public static String getMD5String(String s){
        return getMD5String(s.getBytes());
    }

    /**
     * 判断字符串的md5校验值是否与一个已知的md5码相匹配
     */
    public static boolean checkPassword(String md5, String md5PwdStr){
        return (md5PwdStr).equals(md5);
    }

    /**
     * 生成文件md5校验值
     */
    public static String getFileMD5String(File file) throws IOException{
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messageDigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messageDigest.digest());
    }

    public static String getMD5String(byte[] bytes) {
        messageDigest.update(bytes);
        return bufferToHex(messageDigest.digest());
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        // 取字节中高 4 位的数字转换
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        // 取字节中低 4 位的数字转换
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
