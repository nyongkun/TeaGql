package com.example.tea.shared.error;

public enum ErrorCode {

    TEA_NOT_FOUND("해당 Tea를 찾을 수 없습니다."),
    BRAND_NOT_FOUND("해당 Brand를 찾을 수 없습니다."),
    BRAND_ALREADY_EXISTS("이미 존재하는 Brand 이름입니다."),
    FIELD_VALIDATION("입력값이 올바르지 않습니다.");

    private final String defaultMessage;

    ErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
