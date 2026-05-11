package com.example.tea.catalog.adapter.in.graphql.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateBrandInput(
        @NotNull(message = "Brand id is required.")
        Long id,
        @NotBlank(message = "Brand name is required.")
        String name,
        String country
) {
}
