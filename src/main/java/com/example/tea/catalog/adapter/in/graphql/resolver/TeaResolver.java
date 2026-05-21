package com.example.tea.catalog.adapter.in.graphql.resolver;

import com.example.tea.catalog.adapter.in.graphql.converter.TeaConverter;
import com.example.tea.catalog.adapter.in.graphql.dto.TeaCreateInput;
import com.example.tea.catalog.adapter.in.graphql.dto.TeaUpdateInput;
import com.example.tea.catalog.application.port.in.TeaCommandUseCase;
import com.example.tea.catalog.application.port.in.TeaQueryUseCase;
import com.example.tea.catalog.application.dto.result.TeaResult;
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
public class TeaResolver {

    private final TeaQueryUseCase teaQueryUseCase;
    private final TeaCommandUseCase teaCommandUseCase;

    @QueryMapping
    public List<TeaResult> teas() {
        return teaQueryUseCase.getTeas();
    }

    @QueryMapping
    public TeaResult tea(@Argument @NotNull(message = "Tea id is required.") Long id) {
        return teaQueryUseCase.getTea(TeaConverter.toQuery(id));
    }

    @QueryMapping
    public List<TeaResult> teasByType(@Argument @NotNull(message = "Tea type is required.") TeaType type) {
        return teaQueryUseCase.getTeasByType(TeaConverter.toQuery(type));
    }

    @QueryMapping
    public List<TeaResult> teasByBrand(@Argument @NotBlank(message = "Brand name is required.") String brandName) {
        return teaQueryUseCase.getTeasByBrand(TeaConverter.toQuery(brandName));
    }

    @QueryMapping
    public List<TeaResult> teasByCaffeine(@Argument @NotNull(message = "Caffeine flag is required.") Boolean caffeine) {
        return teaQueryUseCase.getTeasByCaffeine(TeaConverter.toQuery(caffeine));
    }

    @MutationMapping
    public TeaResult createTea(@Valid @Argument TeaCreateInput input) {
        return teaCommandUseCase.createTea(TeaConverter.toCommand(input));
    }

    @MutationMapping
    public TeaResult updateTea(@Valid @Argument TeaUpdateInput input) {
        return teaCommandUseCase.updateTea(TeaConverter.toCommand(input));
    }

    @MutationMapping
    public Boolean deleteTea(@Argument @NotNull(message = "Tea id is required.") Long id) {
        return teaCommandUseCase.deleteTea(id);
    }
}
