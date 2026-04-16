package com.example.tea.catalog.application.exception;

import com.example.tea.shared.error.BaseException;
import com.example.tea.shared.error.ErrorCode;

public class TeaNotFoundException extends BaseException {

    public TeaNotFoundException(Long id) {
        super(ErrorCode.TEA_NOT_FOUND, "Tea not found: " + id);
    }
}
