package com.example.tea.catalog.adapter.out.persistence;

import com.example.tea.catalog.domain.model.Brand;

public class BrandJpaConverter {

    public static Brand toDomain(BrandJpaEntity entity) {
        return new Brand(entity.getId(), entity.getName(), entity.getCountry());
    }

    public static BrandJpaEntity toJpa(Brand domain) {
        BrandJpaEntity entity = new BrandJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setCountry(domain.getCountry());
        return entity;
    }
}
