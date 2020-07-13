package com.centerm.fud_demo.exception;

/**
 * @author jerry
 * 前后密码不一致异常
 */
public class PasswordNotEqualsRetypePasswordException extends Exception {
    private String errorCode;
    private String errorMsg;

    public PasswordNotEqualsRetypePasswordException() {
        super();
    }
}
