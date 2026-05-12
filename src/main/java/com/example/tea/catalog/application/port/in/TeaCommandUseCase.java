package com.example.tea.catalog.application.port.in;

import com.example.tea.catalog.application.dto.command.TeaCreateCommand;
import com.example.tea.catalog.application.dto.command.TeaUpdateCommand;
import com.example.tea.catalog.application.dto.result.TeaResult;

public interface TeaCommandUseCase {

    TeaResult createTea(TeaCreateCommand command);

    TeaResult updateTea(TeaUpdateCommand command);

    boolean deleteTea(Long id);
}
