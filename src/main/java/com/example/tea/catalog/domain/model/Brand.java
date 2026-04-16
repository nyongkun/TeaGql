package com.example.tea.catalog.domain.model;

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
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
