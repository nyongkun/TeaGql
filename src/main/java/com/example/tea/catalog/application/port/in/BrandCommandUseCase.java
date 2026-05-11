package com.example.tea.catalog.application.port.in;

import com.example.tea.catalog.application.dto.command.CreateBrandCommand;
import com.example.tea.catalog.application.dto.command.UpdateBrandCommand;
import com.example.tea.catalog.application.port.in.base.CommandUseCase;
import com.example.tea.catalog.application.dto.result.BrandResult;

public interface BrandCommandUseCase extends CommandUseCase<CreateBrandCommand, UpdateBrandCommand, BrandResult> {

    BrandResult createBrand(CreateBrandCommand command);

    BrandResult updateBrand(UpdateBrandCommand command);

    @Override
    default BrandResult create(CreateBrandCommand command) {
        return createBrand(command);
    }

    @Override
    default BrandResult update(UpdateBrandCommand command) {
        return updateBrand(command);
    }
}
