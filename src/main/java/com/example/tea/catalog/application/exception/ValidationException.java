package com.example.tea.catalog.application.exception;

import com.example.tea.shared.error.BaseException;
import com.example.tea.shared.error.ErrorCode;

public class ValidationException extends BaseException {

    public ValidationException(String message) {
        super(ErrorCode.FIELD_VALIDATION, message);
    }
}
