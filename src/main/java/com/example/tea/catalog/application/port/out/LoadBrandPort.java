package com.example.tea.catalog.application.port.out;

import com.example.tea.catalog.domain.model.Brand;
import java.util.List;
import java.util.Optional;

public interface LoadBrandPort {

    Optional<Brand> loadById(Long id);

    List<Brand> loadAll();

    boolean brandNameExists(String name);
}
