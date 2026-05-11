package com.example.tea.catalog.adapter.in.graphql.converter;

import com.example.tea.catalog.adapter.in.graphql.dto.CreateTeaInput;
import com.example.tea.catalog.adapter.in.graphql.dto.UpdateTeaInput;
import com.example.tea.catalog.application.dto.command.CreateTeaCommand;
import com.example.tea.catalog.application.dto.command.UpdateTeaCommand;
import com.example.tea.catalog.application.dto.query.GetTeaQuery;
import com.example.tea.catalog.application.dto.query.GetTeasByCaffeineQuery;
import com.example.tea.catalog.application.dto.query.GetTeasByBrandQuery;
import com.example.tea.catalog.application.dto.query.GetTeasByTypeQuery;
import com.example.tea.catalog.domain.model.TeaType;

public class TeaGqlConverter {

    public static CreateTeaCommand toCommand(CreateTeaInput input) {
        return new CreateTeaCommand(
                input.name().trim(),
                input.type(),
                input.brandId(),
                trimNullable(input.originCountry()),
                input.caffeine(),
                trimNullable(input.description()),
                input.rating()
        );
    }

    public static UpdateTeaCommand toCommand(UpdateTeaInput input) {
        return new UpdateTeaCommand(
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

    public static GetTeaQuery toQuery(Long id) {
        return new GetTeaQuery(id);
    }

    public static GetTeasByTypeQuery toQuery(TeaType type) {
        return new GetTeasByTypeQuery(type);
    }

    public static GetTeasByBrandQuery toQuery(String brandName) {
        return new GetTeasByBrandQuery(brandName);
    }

    public static GetTeasByCaffeineQuery toQuery(Boolean caffeine) {
        return new GetTeasByCaffeineQuery(caffeine);
    }

    private static String trimNullable(String value) {
        return value != null ? value.trim() : null;
    }
}
