package com.example.tea.catalog.application.exception;

import com.example.tea.global.exception.BizException;
import com.example.tea.global.exception.code.ErrorCode;

public class BrandNotFoundException extends BizException {

    public BrandNotFoundException(Long id) {
        super(ErrorCode.BRAND_NOT_FOUND, "Brand not found: " + id);
    }
}
