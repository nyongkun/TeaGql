package com.example.tea.catalog.application.port.in;

import com.example.tea.catalog.application.dto.command.BrandCreateCommand;
import com.example.tea.catalog.application.dto.command.BrandUpdateCommand;
import com.example.tea.catalog.application.dto.result.BrandResult;

public interface BrandCommandUseCase {

    BrandResult createBrand(BrandCreateCommand command);

    BrandResult updateBrand(BrandUpdateCommand command);
}
