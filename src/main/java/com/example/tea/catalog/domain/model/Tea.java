package com.example.tea.catalog.domain.model;

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
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.originCountry = originCountry;
        this.caffeine = caffeine;
        this.description = description;
        this.rating = rating;
    }
}
