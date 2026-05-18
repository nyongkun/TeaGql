package com.example.tea.catalog.application.port.out;

import com.example.tea.catalog.domain.model.Tea;

public interface SaveTeaPort {

    Tea save(Tea tea);

    void delete(Long id);
}
