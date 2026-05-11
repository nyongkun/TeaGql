package com.example.tea.catalog.application.dto.command;

public record UpdateBrandCommand(Long id, String name, String country) {
}
