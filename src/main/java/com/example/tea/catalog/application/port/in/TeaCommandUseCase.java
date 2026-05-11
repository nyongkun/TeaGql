package com.example.tea.catalog.application.port.in;

import com.example.tea.catalog.application.dto.command.CreateTeaCommand;
import com.example.tea.catalog.application.dto.command.UpdateTeaCommand;
import com.example.tea.catalog.application.port.in.base.CommandUseCase;
import com.example.tea.catalog.application.dto.result.TeaResult;

public interface TeaCommandUseCase extends CommandUseCase<CreateTeaCommand, UpdateTeaCommand, TeaResult> {

    TeaResult createTea(CreateTeaCommand command);

    TeaResult updateTea(UpdateTeaCommand command);

    boolean deleteTea(Long id);

    @Override
    default TeaResult create(CreateTeaCommand command) {
        return createTea(command);
    }

    @Override
    default TeaResult update(UpdateTeaCommand command) {
        return updateTea(command);
    }
}
