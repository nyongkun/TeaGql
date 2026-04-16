package com.example.tea.catalog.application.port.in.base;

import java.util.List;

public interface QueryUseCase<R> {

    List<R> getAll();
}
