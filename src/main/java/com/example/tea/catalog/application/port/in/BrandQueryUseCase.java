package com.example.tea.catalog.application.port.in;

import com.example.tea.catalog.application.dto.result.BrandResult;
import java.util.List;

public interface BrandQueryUseCase {

    List<BrandResult> getBrands();
}
