package com.example.tea.catalog.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.tea.catalog.application.dto.command.TeaCreateCommand;
import com.example.tea.catalog.application.dto.command.TeaUpdateCommand;
import com.example.tea.catalog.application.dto.query.TeaByBrandQuery;
import com.example.tea.catalog.application.dto.query.TeaByCaffeineQuery;
import com.example.tea.catalog.application.dto.query.TeaByIdQuery;
import com.example.tea.catalog.application.dto.query.TeaByTypeQuery;
import com.example.tea.catalog.application.dto.result.TeaResult;
import com.example.tea.catalog.application.exception.BrandNotFoundException;
import com.example.tea.catalog.application.exception.TeaNotFoundException;
import com.example.tea.catalog.application.port.out.LoadBrandPort;
import com.example.tea.catalog.application.port.out.LoadTeaPort;
import com.example.tea.catalog.application.port.out.SaveTeaPort;
import com.example.tea.catalog.domain.model.Brand;
import com.example.tea.catalog.domain.model.Tea;
import com.example.tea.catalog.domain.model.TeaType;
import com.example.tea.global.exception.BizException;
import com.example.tea.global.exception.code.ErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeaUseCaseTest {

    @Mock private LoadTeaPort loadTeaPort;
    @Mock private SaveTeaPort saveTeaPort;
    @Mock private LoadBrandPort loadBrandPort;

    @InjectMocks private TeaApplicationService service;

    // ---- Fixtures ---------------------------------------------

    private Brand testBrand() {
        return new Brand(1L, "Osulloc", "Korea");
    }

    private Tea testTea(Long id) {
        return new Tea(id, "Green Tea", TeaType.GREEN, testBrand(),
                "Korea", true, "desc", 4.5f, LocalDateTime.now());
    }

    // ---- getTea -----------------------------------------------

    // 존재하는 id로 조회하면 TeaResult를 반환
    @Test
    void teaGetSuccessTest() {
        when(loadTeaPort.loadById(1L)).thenReturn(Optional.of(testTea(1L)));

        TeaResult result = service.getTea(new TeaByIdQuery(1L));

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Green Tea");
    }

    // 존재하지 않는 id면 TeaNotFoundException
    @Test
    void teaGetNotFoundTest() {
        when(loadTeaPort.loadById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getTea(new TeaByIdQuery(99L)))
                .isInstanceOf(TeaNotFoundException.class)
                .isInstanceOf(BizException.class)
                .hasMessageContaining("99");
    }

    // 예외의 ErrorCode가 TEA_NOT_FOUND
    @Test
    void teaGetNotFoundErrorCodeTest() {
        when(loadTeaPort.loadById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getTea(new TeaByIdQuery(99L)))
                .isInstanceOf(BizException.class)
                .satisfies(ex -> assertThat(((BizException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.TEA_NOT_FOUND));
    }

    // ---- getTeas / getTeasByType / getTeasByBrand / getTeasByCaffeine ---------

    // 전체 목록을 반환
    @Test
    void teaGetAllSuccessTest() {
        when(loadTeaPort.loadAll()).thenReturn(List.of(testTea(1L), testTea(2L)));

        List<TeaResult> results = service.getTeas();

        assertThat(results).hasSize(2);
    }

    // 타입으로 필터링된 목록을 반환
    @Test
    void teaGetByTypeSuccessTest() {
        when(loadTeaPort.loadByType(TeaType.GREEN)).thenReturn(List.of(testTea(1L)));

        List<TeaResult> results = service.getTeasByType(new TeaByTypeQuery(TeaType.GREEN));

        assertThat(results).hasSize(1);
        assertThat(results.get(0).type()).isEqualTo(TeaType.GREEN);
    }

    // 브랜드명으로 필터링된 목록을 반환
    @Test
    void teaGetByBrandSuccessTest() {
        when(loadTeaPort.loadByBrandName("Osulloc")).thenReturn(List.of(testTea(1L)));

        List<TeaResult> results = service.getTeasByBrand(new TeaByBrandQuery("Osulloc"));

        assertThat(results).hasSize(1);
    }

    // 카페인 여부로 필터링된 목록을 반환
    @Test
    void teaGetByCaffeineSuccessTest() {
        when(loadTeaPort.loadByCaffeine(true)).thenReturn(List.of(testTea(1L)));

        List<TeaResult> results = service.getTeasByCaffeine(new TeaByCaffeineQuery(true));

        assertThat(results).hasSize(1);
    }

    // ---- createTea --------------------------------------------

    // 정상적으로 생성하면 TeaResult를 반환
    @Test
    void teaCreateSuccessTest() {
        TeaCreateCommand command = new TeaCreateCommand(
                "Green Tea", TeaType.GREEN, 1L, "Korea", true, "desc", 4.5f);
        when(loadBrandPort.loadById(1L)).thenReturn(Optional.of(testBrand()));
        when(saveTeaPort.save(any(Tea.class))).thenReturn(testTea(10L));

        TeaResult result = service.createTea(command);

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.name()).isEqualTo("Green Tea");
    }

    // 존재하지 않는 브랜드면 BrandNotFoundException
    @Test
    void teaCreateBrandNotFoundTest() {
        TeaCreateCommand command = new TeaCreateCommand(
                "Green Tea", TeaType.GREEN, 99L, "Korea", true, "desc", 4.5f);
        when(loadBrandPort.loadById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.createTea(command))
                .isInstanceOf(BrandNotFoundException.class)
                .isInstanceOf(BizException.class)
                .hasMessageContaining("99");
    }

    // 브랜드 없을때 ErrorCode가 BRAND_NOT_FOUND
    @Test
    void teaCreateBrandNotFoundErrorCodeTest() {
        TeaCreateCommand command = new TeaCreateCommand(
                "Green Tea", TeaType.GREEN, 99L, "Korea", true, "desc", 4.5f);
        when(loadBrandPort.loadById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.createTea(command))
                .isInstanceOf(BizException.class)
                .satisfies(ex -> assertThat(((BizException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.BRAND_NOT_FOUND));
    }

    // ---- updateTea --------------------------------------------

    // 정상적으로 수정하면 TeaResult를 반환
    @Test
    void teaUpdateSuccessTest() {
        TeaUpdateCommand command = new TeaUpdateCommand(
                1L, "Updated Tea", TeaType.BLACK, 1L, "China", false, "updated", 4.8f);
        when(loadTeaPort.loadById(1L)).thenReturn(Optional.of(testTea(1L)));
        when(loadBrandPort.loadById(1L)).thenReturn(Optional.of(testBrand()));
        when(saveTeaPort.save(any(Tea.class))).thenAnswer(inv -> inv.getArgument(0));

        TeaResult result = service.updateTea(command);

        assertThat(result.name()).isEqualTo("Updated Tea");
    }

    // 존재하지 않는 tea면 TeaNotFoundException
    @Test
    void teaUpdateNotFoundTest() {
        TeaUpdateCommand command = new TeaUpdateCommand(
                99L, "Tea", TeaType.GREEN, 1L, "Korea", true, "desc", 4.0f);
        when(loadTeaPort.loadById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateTea(command))
                .isInstanceOf(TeaNotFoundException.class)
                .isInstanceOf(BizException.class);
    }

    // 존재하지 않는 브랜드면 BrandNotFoundException
    @Test
    void teaUpdateBrandNotFoundTest() {
        TeaUpdateCommand command = new TeaUpdateCommand(
                1L, "Tea", TeaType.GREEN, 99L, "Korea", true, "desc", 4.0f);
        when(loadTeaPort.loadById(1L)).thenReturn(Optional.of(testTea(1L)));
        when(loadBrandPort.loadById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateTea(command))
                .isInstanceOf(BrandNotFoundException.class)
                .isInstanceOf(BizException.class);
    }

    // ---- deleteTea --------------------------------------------

    // 정상적으로 삭제하면 true를 반환
    @Test
    void teaDeleteSuccessTest() {
        when(loadTeaPort.loadById(1L)).thenReturn(Optional.of(testTea(1L)));

        boolean result = service.deleteTea(1L);

        assertThat(result).isTrue();
        verify(saveTeaPort).delete(1L);
    }

    // 존재하지 않는 id면 TeaNotFoundException
    @Test
    void teaDeleteNotFoundTest() {
        when(loadTeaPort.loadById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteTea(99L))
                .isInstanceOf(TeaNotFoundException.class)
                .isInstanceOf(BizException.class);
    }
}
