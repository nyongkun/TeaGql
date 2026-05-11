package com.example.tea.catalog.adapter.in.graphql.dto;

import com.example.tea.catalog.domain.model.TeaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTeaInput(
        @NotNull(message = "Tea id is required.")
        Long id,
        @NotBlank(message = "Tea name is required.")
        String name,
        @NotNull(message = "Tea type is required.")
        TeaType type,
        @NotNull(message = "Brand id is required.")
        Long brandId,
        String originCountry,
        @NotNull(message = "Caffeine flag is required.")
        Boolean caffeine,
        String description,
        Float rating
) {
}
