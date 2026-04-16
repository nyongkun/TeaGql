package com.example.tea.catalog.application.port.in;

import com.example.tea.catalog.application.port.in.base.QueryUseCase;
import com.example.tea.catalog.application.query.GetTeaQuery;
import com.example.tea.catalog.application.query.GetTeasByCaffeineQuery;
import com.example.tea.catalog.application.query.GetTeasByBrandQuery;
import com.example.tea.catalog.application.query.GetTeasByTypeQuery;
import com.example.tea.catalog.application.result.TeaResult;
import java.util.List;

public interface TeaQueryUseCase extends QueryUseCase<TeaResult> {

    List<TeaResult> getTeas();

    TeaResult getTea(GetTeaQuery query);

    List<TeaResult> getTeasByType(GetTeasByTypeQuery query);

    List<TeaResult> getTeasByBrand(GetTeasByBrandQuery query);

    List<TeaResult> getTeasByCaffeine(GetTeasByCaffeineQuery query);

    @Override
    default List<TeaResult> getAll() {
        return getTeas();
    }
}
