package com.example.tea.catalog.domain.model;

import com.example.tea.global.exception.DomainException;
import com.example.tea.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Brand {

    private Long id;
    private String name;
    private String country;

    public Brand() {
    }

    public Brand(Long id, String name, String country) {
        if (name == null || name.isBlank())
            throw new DomainException(ErrorCode.BRAND_NAME_REQUIRED);
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
