package com.example.tea.catalog.adapter.in.graphql;

import com.example.tea.catalog.application.port.in.TeaCommandUseCase;
import com.example.tea.catalog.application.port.in.TeaQueryUseCase;
import com.example.tea.catalog.application.result.TeaResult;
import com.example.tea.catalog.domain.model.TeaType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TeaGraphqlResolver {

    private final TeaQueryUseCase teaQueryUseCase;
    private final TeaCommandUseCase teaCommandUseCase;

    @QueryMapping
    public List<TeaResult> teas() {
        return teaQueryUseCase.getTeas();
    }

    @QueryMapping
    public TeaResult tea(@Argument @NotNull(message = "Tea id is required.") Long id) {
        return teaQueryUseCase.getTea(TeaGqlConverter.toQuery(id));
    }

    @QueryMapping
    public List<TeaResult> teasByType(@Argument @NotNull(message = "Tea type is required.") TeaType type) {
        return teaQueryUseCase.getTeasByType(TeaGqlConverter.toQuery(type));
    }

    @QueryMapping
    public List<TeaResult> teasByBrand(@Argument @NotBlank(message = "Brand name is required.") String brandName) {
        return teaQueryUseCase.getTeasByBrand(TeaGqlConverter.toQuery(brandName));
    }

    @QueryMapping
    public List<TeaResult> teasByCaffeine(@Argument @NotNull(message = "Caffeine flag is required.") Boolean caffeine) {
        return teaQueryUseCase.getTeasByCaffeine(TeaGqlConverter.toQuery(caffeine));
    }

    @MutationMapping
    public TeaResult createTea(@Valid @Argument CreateTeaInput input) {
        return teaCommandUseCase.createTea(TeaGqlConverter.toCommand(input));
    }

    @MutationMapping
    public TeaResult updateTea(@Valid @Argument UpdateTeaInput input) {
        return teaCommandUseCase.updateTea(TeaGqlConverter.toCommand(input));
    }

    @MutationMapping
    public Boolean deleteTea(@Argument @NotNull(message = "Tea id is required.") Long id) {
        return teaCommandUseCase.deleteTea(id);
    }
}
