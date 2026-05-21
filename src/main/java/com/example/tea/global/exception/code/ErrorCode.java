package com.example.tea.global.exception.code;

public enum ErrorCode {

    TEA_NOT_FOUND("해당 Tea를 찾을 수 없습니다."),
    BRAND_NOT_FOUND("해당 Brand를 찾을 수 없습니다."),
    BRAND_ALREADY_EXISTS("이미 존재하는 Brand 이름입니다."),
    FIELD_VALIDATION("입력값이 올바르지 않습니다."),
    BRAND_NAME_REQUIRED("브랜드 이름은 필수입니다."),
    TEA_NAME_REQUIRED("티 이름은 필수입니다."),
    TEA_TYPE_REQUIRED("티 타입은 필수입니다."),
    TEA_BRAND_REQUIRED("브랜드는 필수입니다."),
    TEA_CAFFEINE_REQUIRED("카페인 여부는 필수입니다."),
    PERSISTENCE_ERROR("데이터 처리 중 오류가 발생했습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getCode() {
        return name();
    }

    public String getMessage() {
        return message;
    }
}
