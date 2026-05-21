package com.example.tea.catalog.adapter.in.graphql.converter;

import com.example.tea.catalog.adapter.in.graphql.dto.TeaCreateInput;
import com.example.tea.catalog.adapter.in.graphql.dto.TeaUpdateInput;
import com.example.tea.catalog.application.dto.command.TeaCreateCommand;
import com.example.tea.catalog.application.dto.command.TeaUpdateCommand;
import com.example.tea.catalog.application.dto.query.TeaByIdQuery;
import com.example.tea.catalog.application.dto.query.TeaByCaffeineQuery;
import com.example.tea.catalog.application.dto.query.TeaByBrandQuery;
import com.example.tea.catalog.application.dto.query.TeaByTypeQuery;
import com.example.tea.catalog.domain.model.TeaType;

public class TeaConverter {

    public static TeaCreateCommand toCommand(TeaCreateInput input) {
        return new TeaCreateCommand(
                input.name().trim(),
                input.type(),
                input.brandId(),
                trimNullable(input.originCountry()),
                input.caffeine(),
                trimNullable(input.description()),
                input.rating()
        );
    }

    public static TeaUpdateCommand toCommand(TeaUpdateInput input) {
        return new TeaUpdateCommand(
                input.id(),
                input.name().trim(),
                input.type(),
                input.brandId(),
                trimNullable(input.originCountry()),
                input.caffeine(),
                trimNullable(input.description()),
                input.rating()
        );
    }

    public static TeaByIdQuery toQuery(Long id) {
        return new TeaByIdQuery(id);
    }

    public static TeaByTypeQuery toQuery(TeaType type) {
        return new TeaByTypeQuery(type);
    }

    public static TeaByBrandQuery toQuery(String brandName) {
        return new TeaByBrandQuery(brandName);
    }

    public static TeaByCaffeineQuery toQuery(Boolean caffeine) {
        return new TeaByCaffeineQuery(caffeine);
    }

    private static String trimNullable(String value) {
        return value != null ? value.trim() : null;
    }
}
