package com.example.tea.global.exception;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.tea.global.exception.code.ErrorCode;
import org.junit.jupiter.api.Test;

class ErrorResponseTest {

    // ErrorCode로 생성하면 code와 message를 가진다
    @Test
    void errorResponseHasCodeAndMessageTest() {
        ErrorResponse response = ErrorResponse.of(ErrorCode.TEA_NOT_FOUND);

        assertThat(response.code()).isEqualTo("TEA_NOT_FOUND");
        assertThat(response.message()).isEqualTo(ErrorCode.TEA_NOT_FOUND.getMessage());
    }

    // 커스텀 메세지로 생성하면 해당 메세지를 가진다
    @Test
    void errorResponseHoldsCustomMessageTest() {
        ErrorResponse response = ErrorResponse.of(ErrorCode.TEA_NOT_FOUND, "Tea ID 99를 찾을 수 없습니다");

        assertThat(response.code()).isEqualTo("TEA_NOT_FOUND");
        assertThat(response.message()).isEqualTo("Tea ID 99를 찾을 수 없습니다");
    }

    // code는 ErrorCode의 name과 일치한다
    @Test
    void errorResponseCodeEqualsErrorCodeNameTest() {
        ErrorResponse response = ErrorResponse.of(ErrorCode.BRAND_NOT_FOUND);

        assertThat(response.code()).isEqualTo(ErrorCode.BRAND_NOT_FOUND.name());
    }
}
