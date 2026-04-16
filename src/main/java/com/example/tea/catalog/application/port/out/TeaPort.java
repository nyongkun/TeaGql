package com.example.tea.catalog.application.port.out;

import com.example.tea.catalog.domain.model.Tea;
import com.example.tea.catalog.domain.model.TeaType;
import java.util.List;
import java.util.Optional;

public interface TeaPort {

    Optional<Tea> findById(Long id);

    List<Tea> findAll();

    List<Tea> findByType(TeaType type);

    List<Tea> findByBrandName(String brandName);

    List<Tea> findByCaffeine(Boolean caffeine);

    Tea save(Tea tea);

    void delete(Long id);
}
