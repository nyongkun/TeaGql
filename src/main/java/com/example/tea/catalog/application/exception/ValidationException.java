package com.example.tea.catalog.application.exception;

import com.example.tea.global.exception.BizException;
import com.example.tea.global.exception.code.ErrorCode;

public class ValidationException extends BizException {

    public ValidationException(String message) {
        super(ErrorCode.FIELD_VALIDATION, message);
    }
}
