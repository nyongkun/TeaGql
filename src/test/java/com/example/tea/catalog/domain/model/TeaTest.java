package com.example.tea.catalog.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.tea.global.exception.DomainException;
import com.example.tea.global.exception.code.ErrorCode;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TeaTest {

    private Brand testBrand() {
        return new Brand(1L, "Osulloc", "Korea");
    }

    // ---- 생성 --------------------------------------------

    // 유효한 값으로 생성하면 Tea가 반환
    @Test
    void teaCreateValidTest() {
        Tea tea = new Tea(1L, "Green Tea", TeaType.GREEN, testBrand(), "Korea", true, "desc", 4.5f, LocalDateTime.now());

        assertThat(tea.getName()).isEqualTo("Green Tea");
        assertThat(tea.getType()).isEqualTo(TeaType.GREEN);
    }

    // name이 null이면 DomainException
    @Test
    void teaCreateNullNameTest() {
        assertThatThrownBy(() -> new Tea(1L, null, TeaType.GREEN, testBrand(), "Korea", true, "desc", 4.5f, LocalDateTime.now()))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.TEA_NAME_REQUIRED));
    }

    // name이 빈 문자열이면 DomainException
    @Test
    void teaCreateBlankNameTest() {
        assertThatThrownBy(() -> new Tea(1L, "  ", TeaType.GREEN, testBrand(), "Korea", true, "desc", 4.5f, LocalDateTime.now()))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.TEA_NAME_REQUIRED));
    }

    // type이 null이면 DomainException
    @Test
    void teaCreateNullTypeTest() {
        assertThatThrownBy(() -> new Tea(1L, "Green Tea", null, testBrand(), "Korea", true, "desc", 4.5f, LocalDateTime.now()))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.TEA_TYPE_REQUIRED));
    }

    // brand가 null이면 DomainException
    @Test
    void teaCreateNullBrandTest() {
        assertThatThrownBy(() -> new Tea(1L, "Green Tea", TeaType.GREEN, null, "Korea", true, "desc", 4.5f, LocalDateTime.now()))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.TEA_BRAND_REQUIRED));
    }

    // caffeine이 null이면 DomainException
    @Test
    void teaCreateNullCaffeineTest() {
        assertThatThrownBy(() -> new Tea(1L, "Green Tea", TeaType.GREEN, testBrand(), "Korea", null, "desc", 4.5f, LocalDateTime.now()))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.TEA_CAFFEINE_REQUIRED));
    }

    // ---- 수정 --------------------------------------------

    // 유효한 값으로 수정하면 Tea가 수정
    @Test
    void teaUpdateValidTest() {
        Tea tea = new Tea(1L, "Green Tea", TeaType.GREEN, testBrand(), "Korea", true, "desc", 4.5f, LocalDateTime.now());

        tea.update("Updated Tea", TeaType.BLACK, testBrand(), "China", false, "updated", 4.8f);

        assertThat(tea.getName()).isEqualTo("Updated Tea");
        assertThat(tea.getType()).isEqualTo(TeaType.BLACK);
    }

    // update 시 name이 null이면 DomainException
    @Test
    void teaUpdateNullNameTest() {
        Tea tea = new Tea(1L, "Green Tea", TeaType.GREEN, testBrand(), "Korea", true, "desc", 4.5f, LocalDateTime.now());

        assertThatThrownBy(() -> tea.update(null, TeaType.BLACK, testBrand(), "China", false, "updated", 4.8f))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.TEA_NAME_REQUIRED));
    }

    // update 시 type이 null이면 DomainException
    @Test
    void teaUpdateNullTypeTest() {
        Tea tea = new Tea(1L, "Green Tea", TeaType.GREEN, testBrand(), "Korea", true, "desc", 4.5f, LocalDateTime.now());

        assertThatThrownBy(() -> tea.update("Green Tea", null, testBrand(), "Korea", true, "desc", 4.5f))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.TEA_TYPE_REQUIRED));
    }

    // update 시 brand가 null이면 DomainException
    @Test
    void teaUpdateNullBrandTest() {
        Tea tea = new Tea(1L, "Green Tea", TeaType.GREEN, testBrand(), "Korea", true, "desc", 4.5f, LocalDateTime.now());

        assertThatThrownBy(() -> tea.update("Green Tea", TeaType.GREEN, null, "Korea", true, "desc", 4.5f))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.TEA_BRAND_REQUIRED));
    }

    // update 시 caffeine이 null이면 DomainException
    @Test
    void teaUpdateNullCaffeineTest() {
        Tea tea = new Tea(1L, "Green Tea", TeaType.GREEN, testBrand(), "Korea", true, "desc", 4.5f, LocalDateTime.now());

        assertThatThrownBy(() -> tea.update("Green Tea", TeaType.GREEN, testBrand(), "Korea", null, "desc", 4.5f))
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> assertThat(((DomainException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.TEA_CAFFEINE_REQUIRED));
    }
}
