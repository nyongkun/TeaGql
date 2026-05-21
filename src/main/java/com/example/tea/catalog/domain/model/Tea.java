package com.example.tea.catalog.domain.model;

import com.example.tea.global.exception.DomainException;
import com.example.tea.global.exception.code.ErrorCode;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tea {

    private Long id;
    private String name;
    private TeaType type;
    private Brand brand;
    private String originCountry;
    private Boolean caffeine;
    private String description;
    private Float rating;
    private LocalDateTime createdAt;

    public Tea() {
    }

    public Tea(Long id, String name, TeaType type, Brand brand, String originCountry,
               Boolean caffeine, String description, Float rating, LocalDateTime createdAt) {
        validate(name, type, brand, caffeine);
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.originCountry = originCountry;
        this.caffeine = caffeine;
        this.description = description;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public void update(String name, TeaType type, Brand brand, String originCountry,
                       Boolean caffeine, String description, Float rating) {
        validate(name, type, brand, caffeine);
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.originCountry = originCountry;
        this.caffeine = caffeine;
        this.description = description;
        this.rating = rating;
    }

    private void validate(String name, TeaType type, Brand brand, Boolean caffeine) {
        if (name == null || name.isBlank()) throw new DomainException(ErrorCode.TEA_NAME_REQUIRED);
        if (type == null) throw new DomainException(ErrorCode.TEA_TYPE_REQUIRED);
        if (brand == null) throw new DomainException(ErrorCode.TEA_BRAND_REQUIRED);
        if (caffeine == null) throw new DomainException(ErrorCode.TEA_CAFFEINE_REQUIRED);
    }
}
