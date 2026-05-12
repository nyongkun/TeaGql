package com.example.tea.catalog.adapter.in.graphql.resolver;

import com.example.tea.catalog.adapter.in.graphql.converter.BrandGraphqlConverter;
import com.example.tea.catalog.adapter.in.graphql.dto.BrandCreateInput;
import com.example.tea.catalog.adapter.in.graphql.dto.BrandUpdateInput;
import com.example.tea.catalog.application.port.in.BrandCommandUseCase;
import com.example.tea.catalog.application.port.in.BrandQueryUseCase;
import com.example.tea.catalog.application.dto.result.BrandResult;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Validated
@RequiredArgsConstructor
public class BrandGraphqlResolver {

    private final BrandQueryUseCase brandQueryUseCase;
    private final BrandCommandUseCase brandCommandUseCase;

    @QueryMapping
    public List<BrandResult> brands() {
        return brandQueryUseCase.getBrands();
    }

    @MutationMapping
    public BrandResult createBrand(@Valid @Argument BrandCreateInput input) {
        return brandCommandUseCase.createBrand(BrandGraphqlConverter.toCommand(input));
    }

    @MutationMapping
    public BrandResult updateBrand(@Valid @Argument BrandUpdateInput input) {
        return brandCommandUseCase.updateBrand(BrandGraphqlConverter.toCommand(input));
    }
}
