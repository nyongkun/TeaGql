package com.example.tea.catalog.application.port.in;

import com.example.tea.catalog.application.port.in.base.QueryUseCase;
import com.example.tea.catalog.application.result.BrandResult;
import java.util.List;

public interface BrandQueryUseCase extends QueryUseCase<BrandResult> {

    List<BrandResult> getBrands();

    @Override
    default List<BrandResult> getAll() {
        return getBrands();
    }
}
