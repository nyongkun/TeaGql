package com.example.tea.catalog.application.dto.command;

import com.example.tea.catalog.domain.model.TeaType;

public record UpdateTeaCommand(
        Long id,
        String name,
        TeaType type,
        Long brandId,
        String originCountry,
        Boolean caffeine,
        String description,
        Float rating
) {
}
