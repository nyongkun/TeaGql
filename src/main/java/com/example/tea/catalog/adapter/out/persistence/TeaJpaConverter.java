package com.example.tea.catalog.adapter.out.persistence;

import com.example.tea.catalog.domain.model.Tea;

public class TeaJpaConverter {

    public static Tea toDomain(TeaJpaEntity entity) {
        return new Tea(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                BrandJpaConverter.toDomain(entity.getBrand()),
                entity.getOriginCountry(),
                entity.getCaffeine(),
                entity.getDescription(),
                entity.getRating(),
                entity.getCreatedAt()
        );
    }
}
