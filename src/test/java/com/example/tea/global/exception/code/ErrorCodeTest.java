package com.example.tea.global.exception.code;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ErrorCodeTest {

    // 모든 에러코드는 code값을 가진다
    @Test
    void errorCodeHasCodeValueTest() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            assertThat(errorCode.getCode()).isNotBlank();
        }
    }

    // 모든 에러코드는 메세지를 가진다
    @Test
    void errorCodeHasMessageTest() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            assertThat(errorCode.getMessage()).isNotBlank();
        }
    }

    // 에러코드 이름과 code값이 일치한다
    @Test
    void errorCodeNameEqualsCodeTest() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            assertThat(errorCode.getCode()).isEqualTo(errorCode.name());
        }
    }

    // TEA_NOT_FOUND 코드와 메세지가 동일하다
    @Test
    void teaNotFoundErrorCodeValidTest() {
        assertThat(ErrorCode.TEA_NOT_FOUND.getCode()).isEqualTo("TEA_NOT_FOUND");
        assertThat(ErrorCode.TEA_NOT_FOUND.getMessage()).isNotBlank();
    }

    // BRAND_NOT_FOUND 코드와 메세지가 동일하다
    @Test
    void brandNotFoundErrorCodeValidTest() {
        assertThat(ErrorCode.BRAND_NOT_FOUND.getCode()).isEqualTo("BRAND_NOT_FOUND");
        assertThat(ErrorCode.BRAND_NOT_FOUND.getMessage()).isNotBlank();
    }

    // BRAND_NAME_REQUIRED 코드와 메세지가 존재한다
    @Test
    void brandNameRequiredErrorCodeValidTest() {
        assertThat(ErrorCode.BRAND_NAME_REQUIRED.getCode()).isEqualTo("BRAND_NAME_REQUIRED");
        assertThat(ErrorCode.BRAND_NAME_REQUIRED.getMessage()).isNotBlank();
    }

    // TEA_NAME_REQUIRED 코드와 메세지가 존재한다
    @Test
    void teaNameRequiredErrorCodeValidTest() {
        assertThat(ErrorCode.TEA_NAME_REQUIRED.getCode()).isEqualTo("TEA_NAME_REQUIRED");
        assertThat(ErrorCode.TEA_NAME_REQUIRED.getMessage()).isNotBlank();
    }

    // TEA_TYPE_REQUIRED 코드와 메세지가 존재한다
    @Test
    void teaTypeRequiredErrorCodeValidTest() {
        assertThat(ErrorCode.TEA_TYPE_REQUIRED.getCode()).isEqualTo("TEA_TYPE_REQUIRED");
        assertThat(ErrorCode.TEA_TYPE_REQUIRED.getMessage()).isNotBlank();
    }

    // TEA_BRAND_REQUIRED 코드와 메세지가 존재한다
    @Test
    void teaBrandRequiredErrorCodeValidTest() {
        assertThat(ErrorCode.TEA_BRAND_REQUIRED.getCode()).isEqualTo("TEA_BRAND_REQUIRED");
        assertThat(ErrorCode.TEA_BRAND_REQUIRED.getMessage()).isNotBlank();
    }

    // TEA_CAFFEINE_REQUIRED 코드와 메세지가 존재한다
    @Test
    void teaCaffeineRequiredErrorCodeValidTest() {
        assertThat(ErrorCode.TEA_CAFFEINE_REQUIRED.getCode()).isEqualTo("TEA_CAFFEINE_REQUIRED");
        assertThat(ErrorCode.TEA_CAFFEINE_REQUIRED.getMessage()).isNotBlank();
    }

    // PERSISTENCE_ERROR 코드와 메세지가 존재한다
    @Test
    void persistenceErrorErrorCodeValidTest() {
        assertThat(ErrorCode.PERSISTENCE_ERROR.getCode()).isEqualTo("PERSISTENCE_ERROR");
        assertThat(ErrorCode.PERSISTENCE_ERROR.getMessage()).isNotBlank();
    }
}
