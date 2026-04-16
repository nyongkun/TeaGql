package com.example.tea.catalog.adapter.in.graphql;

import com.example.tea.catalog.application.command.CreateTeaCommand;
import com.example.tea.catalog.application.command.UpdateTeaCommand;
import com.example.tea.catalog.application.query.GetTeaQuery;
import com.example.tea.catalog.application.query.GetTeasByCaffeineQuery;
import com.example.tea.catalog.application.query.GetTeasByBrandQuery;
import com.example.tea.catalog.application.query.GetTeasByTypeQuery;
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
