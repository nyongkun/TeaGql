package com.example.tea.global.exception;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.tea.global.exception.code.ErrorCode;
import org.junit.jupiter.api.Test;

class BizExceptionTest {

    // ErrorCode로 생성하면 해당 ErrorCode를 가진다
    @Test
    void bizExceptionHoldsErrorCodeTest() {
        BizException ex = new BizException(ErrorCode.BRAND_ALREADY_EXISTS);

        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.BRAND_ALREADY_EXISTS);
    }

    // 커스텀 메세지로 생성
    @Test
    void bizExceptionHoldsCustomMessageTest() {
        BizException ex = new BizException(ErrorCode.BRAND_ALREADY_EXISTS, "Osulloc 브랜드가 이미 존재합니다");

        assertThat(ex.getMessage()).isEqualTo("Osulloc 브랜드가 이미 존재합니다");
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.BRAND_ALREADY_EXISTS);
    }
}
