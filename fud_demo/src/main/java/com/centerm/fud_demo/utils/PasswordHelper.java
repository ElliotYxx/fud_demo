package com.centerm.fud_demo.utils;

import com.centerm.fud_demo.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {
    //定义算法md5
    private static String algorithmName="MD5";
    private final static int hashIterations=5;
    public static void encryptPassword(User user)
    {
        String newPassword=new SimpleHash(algorithmName,user.getPassword(), ByteSource.Util.bytes(user.getUsername()),hashIterations).toHex();
        user.setPassword(newPassword);
    }
}
