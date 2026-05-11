package com.example.tea.catalog.adapter.in.graphql.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateBrandInput(
        @NotBlank(message = "Brand name is required.")
        String name,
        String country
) {
}
