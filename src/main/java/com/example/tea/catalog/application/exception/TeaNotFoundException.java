package com.example.tea.catalog.application.exception;

import com.example.tea.global.exception.BizException;
import com.example.tea.global.exception.code.ErrorCode;

public class TeaNotFoundException extends BizException {

    public TeaNotFoundException(Long id) {
        super(ErrorCode.TEA_NOT_FOUND, "Tea not found: " + id);
    }
}
