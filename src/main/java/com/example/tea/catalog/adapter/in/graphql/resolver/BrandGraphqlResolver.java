package com.example.tea.catalog.adapter.in.graphql.resolver;

import com.example.tea.catalog.adapter.in.graphql.converter.BrandGqlConverter;
import com.example.tea.catalog.adapter.in.graphql.dto.CreateBrandInput;
import com.example.tea.catalog.adapter.in.graphql.dto.UpdateBrandInput;
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
    public BrandResult createBrand(@Valid @Argument CreateBrandInput input) {
        return brandCommandUseCase.createBrand(BrandGqlConverter.toCommand(input));
    }

    @MutationMapping
    public BrandResult updateBrand(@Valid @Argument UpdateBrandInput input) {
        return brandCommandUseCase.updateBrand(BrandGqlConverter.toCommand(input));
    }
}
