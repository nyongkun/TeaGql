package com.example.tea.catalog.application.exception;

import com.example.tea.shared.error.BaseException;
import com.example.tea.shared.error.ErrorCode;

public class BrandNotFoundException extends BaseException {

    public BrandNotFoundException(Long id) {
        super(ErrorCode.BRAND_NOT_FOUND, "Brand not found: " + id);
    }
}
