package com.example.tea.catalog.application.port.in;

import com.example.tea.catalog.application.dto.query.TeaByIdQuery;
import com.example.tea.catalog.application.dto.query.TeaByCaffeineQuery;
import com.example.tea.catalog.application.dto.query.TeaByBrandQuery;
import com.example.tea.catalog.application.dto.query.TeaByTypeQuery;
import com.example.tea.catalog.application.dto.result.TeaResult;
import java.util.List;

public interface TeaQueryUseCase {

    List<TeaResult> getTeas();

    TeaResult getTea(TeaByIdQuery query);

    List<TeaResult> getTeasByType(TeaByTypeQuery query);

    List<TeaResult> getTeasByBrand(TeaByBrandQuery query);

    List<TeaResult> getTeasByCaffeine(TeaByCaffeineQuery query);
}
