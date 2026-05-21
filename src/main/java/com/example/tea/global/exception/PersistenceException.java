package com.example.tea.global.exception;

import com.example.tea.global.exception.code.ErrorCode;

public class PersistenceException extends RuntimeException {

    private final ErrorCode errorCode;

    public PersistenceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public PersistenceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
