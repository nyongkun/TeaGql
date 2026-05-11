package com.example.tea.catalog.adapter.in.graphql.converter;

import com.example.tea.catalog.adapter.in.graphql.dto.CreateBrandInput;
import com.example.tea.catalog.adapter.in.graphql.dto.UpdateBrandInput;
import com.example.tea.catalog.application.dto.command.CreateBrandCommand;
import com.example.tea.catalog.application.dto.command.UpdateBrandCommand;

public class BrandGqlConverter {

    public static CreateBrandCommand toCommand(CreateBrandInput input) {
        //DDD에서사용하는 명명규칙 확인후 리팩토링 (BrandCommand)
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
