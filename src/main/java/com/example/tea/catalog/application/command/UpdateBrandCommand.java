package com.example.tea.catalog.application.command;

public record UpdateBrandCommand(Long id, String name, String country) {
}
