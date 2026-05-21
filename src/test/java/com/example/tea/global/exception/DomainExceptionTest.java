package com.example.tea.global.exception;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.tea.global.exception.code.ErrorCode;
import org.junit.jupiter.api.Test;

class DomainExceptionTest {

    // ErrorCode로 생성하면 해당 ErrorCode를 가진다
    @Test
    void domainExceptionHoldsErrorCodeTest() {
        DomainException ex = new DomainException(ErrorCode.TEA_NOT_FOUND);

        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.TEA_NOT_FOUND);
    }

}
