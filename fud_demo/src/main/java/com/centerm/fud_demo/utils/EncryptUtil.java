package com.centerm.fud_demo.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/19 下午4:54
 * 文件加/解密工具类
 *
 */
public class EncryptUtil {
    /**
     * 加密算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 转换格式
     */
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    /**
     * 加密key
     */
    private static final String key = "1234567887654321";

    /**
     * 利用16个字节128位的key给src加密
     * @param src 需要加密的字节码文件
     * @return 加密后的字节码文件
     */
    @SuppressWarnings("unused")
    public static byte[] encrypt(byte[] src)
    {
        try {
            //加密
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(),ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec,new SecureRandom());
            byte[] enMsgBytes = cipher.doFinal(src);
            return enMsgBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 利用16个字节128位的key给src解密
     * @param encryptBytes  加密后的字节码文件
     * @return 解密后的字节码文件
     */
    @SuppressWarnings("unused")
    public static byte[] decrypt(byte[] encryptBytes){
        try {
            //解密
            Cipher deCipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(),ALGORITHM);
            deCipher.init(Cipher.DECRYPT_MODE, keySpec,new SecureRandom());
            byte[] deMsgBytes = deCipher.doFinal(encryptBytes);
            return deMsgBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
