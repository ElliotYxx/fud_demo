package com.centerm.fud_demo.exception;

import lombok.Data;

@Data
public class SuperVipRemoveAdminException extends Exception {
    private String errorCode;
    private String errorMsg;

    public SuperVipRemoveAdminException() {
    }

    public SuperVipRemoveAdminException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
