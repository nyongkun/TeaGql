package com.example.tea.catalog.application.exception;

import com.example.tea.global.exception.BizException;
import com.example.tea.global.exception.code.ErrorCode;

public class BrandAlreadyExistsException extends BizException {

    public BrandAlreadyExistsException(String name) {
        super(ErrorCode.BRAND_ALREADY_EXISTS, "Brand already exists: " + name);
    }
}
