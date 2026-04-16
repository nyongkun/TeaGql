package com.example.tea.catalog.adapter.in.graphql;

import com.example.tea.catalog.application.command.CreateBrandCommand;
import com.example.tea.catalog.application.command.UpdateBrandCommand;

public class BrandGqlConverter {

    public static CreateBrandCommand toCommand(CreateBrandInput input) {
        return new CreateBrandCommand(
                input.name().trim(),
                trimNullable(input.country())
        );
    }

    public static UpdateBrandCommand toCommand(UpdateBrandInput input) {
        return new UpdateBrandCommand(
                input.id(),
                input.name().trim(),
                trimNullable(input.country())
        );
    }

    private static String trimNullable(String value) {
        return value != null ? value.trim() : null;
    }
}
