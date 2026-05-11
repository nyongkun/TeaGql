package com.example.tea.catalog.application.dto.result;

import com.example.tea.catalog.domain.model.TeaType;
import java.time.LocalDateTime;

public record TeaResult(
        Long id,
        String name,
        TeaType type,
        BrandResult brand,
        String originCountry,
        Boolean caffeine,
        String description,
        Float rating,
        LocalDateTime createdAt
) {
}
