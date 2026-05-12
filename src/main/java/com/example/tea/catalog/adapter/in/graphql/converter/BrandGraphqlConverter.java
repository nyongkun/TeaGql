package com.example.tea.catalog.adapter.in.graphql.converter;

import com.example.tea.catalog.adapter.in.graphql.dto.BrandCreateInput;
import com.example.tea.catalog.adapter.in.graphql.dto.BrandUpdateInput;
import com.example.tea.catalog.application.dto.command.BrandCreateCommand;
import com.example.tea.catalog.application.dto.command.BrandUpdateCommand;

public class BrandGraphqlConverter {

    public static BrandCreateCommand toCommand(BrandCreateInput input) {
        return new BrandCreateCommand(
                input.name().trim(),
                trimNullable(input.country())
        );
    }

    public static BrandUpdateCommand toCommand(BrandUpdateInput input) {
        return new BrandUpdateCommand(
                input.id(),
                input.name().trim(),
                trimNullable(input.country())
        );
    }

    private static String trimNullable(String value) {
        return value != null ? value.trim() : null;
    }
}
