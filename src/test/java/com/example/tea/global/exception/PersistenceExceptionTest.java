package com.example.tea.global.exception;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.tea.global.exception.code.ErrorCode;
import org.junit.jupiter.api.Test;

class PersistenceExceptionTest {

    // ErrorCode로 생성하면 해당 ErrorCode를 가진다
    @Test
    void persistenceExceptionHoldsErrorCodeTest() {
        PersistenceException ex = new PersistenceException(ErrorCode.TEA_NOT_FOUND);

        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.TEA_NOT_FOUND);
    }

    // Throwable cause로 생성하면 ErrorCode와 cause를 함께 보유한다
    // BrandJpaAdapter, TeaJpaAdapter에서 DataAccessException을 감쌀 때 사용
    @Test
    void persistenceExceptionHoldsCauseTest() {
        RuntimeException cause = new RuntimeException("DB connection failed");
        PersistenceException ex = new PersistenceException(ErrorCode.PERSISTENCE_ERROR, cause);

        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.PERSISTENCE_ERROR);
        assertThat(ex.getCause()).isEqualTo(cause);
    }
}
