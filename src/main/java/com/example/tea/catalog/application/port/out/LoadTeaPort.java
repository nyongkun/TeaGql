package com.example.tea.catalog.application.port.out;

import com.example.tea.catalog.domain.model.Tea;
import com.example.tea.catalog.domain.model.TeaType;
import java.util.List;
import java.util.Optional;

public interface LoadTeaPort {

    Optional<Tea> loadById(Long id);

    List<Tea> loadAll();

    List<Tea> loadByType(TeaType type);

    List<Tea> loadByBrandName(String brandName);

    List<Tea> loadByCaffeine(Boolean caffeine);
}
