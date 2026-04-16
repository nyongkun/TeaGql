package com.example.tea.catalog.application.port.out;

import com.example.tea.catalog.domain.model.Brand;
import java.util.List;
import java.util.Optional;

public interface BrandPort {

    Optional<Brand> findById(Long id);

    List<Brand> findAll();

    boolean existsByName(String name);

    Brand save(Brand brand);
}
