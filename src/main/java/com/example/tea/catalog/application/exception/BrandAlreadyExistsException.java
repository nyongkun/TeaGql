package com.example.tea.catalog.application.exception;

import com.example.tea.shared.error.BaseException;
import com.example.tea.shared.error.ErrorCode;

public class BrandAlreadyExistsException extends BaseException {

    public BrandAlreadyExistsException(String name) {
        super(ErrorCode.BRAND_ALREADY_EXISTS, "Brand already exists: " + name);
    }

}
