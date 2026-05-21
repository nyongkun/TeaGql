package com.example.tea.catalog.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.tea.global.exception.DomainException;
import com.example.tea.global.exception.code.ErrorCode;
import org.junit.jupiter.api.Test;

class BrandTest {

    // ---- 생성 --------------------------------------------

    // 유효한 값으로 생성하면 Brand가 반환
    @Test
    void brandCreateValidTest() {
        Brand brand = new Brand(1L, "Osulloc", "Korea");

        assertThat(brand.getName()).isEqualTo("Osulloc");
        assertThat(brand.getCountry()).isEqualTo("Korea");
    }

    // name이 null이면 DomainException
    @Test
    void brandCreateNullNameTest() {
        assertThatThrownBy(() -> new Brand(1L, null, "Korea"))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.BRAND_NAME_REQUIRED));
    }

    // name이 빈 문자열이면 DomainException
    @Test
    void brandCreateBlankNameTest() {
        assertThatThrownBy(() -> new Brand(1L, "  ", "Korea"))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.BRAND_NAME_REQUIRED));
    }
}
