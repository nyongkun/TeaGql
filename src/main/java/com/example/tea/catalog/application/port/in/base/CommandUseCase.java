package com.example.tea.catalog.application.port.in.base;

public interface CommandUseCase<C, U, R> {

    R create(C command);

    R update(U command);
}
