package com.example.tea.catalog.application.port.out;

import com.example.tea.catalog.domain.model.Brand;

public interface SaveBrandPort {

    Brand save(Brand brand);
}
