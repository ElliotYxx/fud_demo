package com.centerm.fud_demo.exception;

import lombok.Data;

/**
 * 未接受协议异常
 */
@Data
public class NotAcceptTermsException extends Exception {
    private String errorCode;
    private String errorMsg;
    public NotAcceptTermsException() {
    }
}
