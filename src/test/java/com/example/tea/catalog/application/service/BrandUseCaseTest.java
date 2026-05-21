package com.example.tea.catalog.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.tea.catalog.application.dto.command.BrandCreateCommand;
import com.example.tea.catalog.application.dto.command.BrandUpdateCommand;
import com.example.tea.catalog.application.dto.result.BrandResult;
import com.example.tea.catalog.application.exception.BrandAlreadyExistsException;
import com.example.tea.catalog.application.exception.BrandNotFoundException;
import com.example.tea.catalog.application.port.out.LoadBrandPort;
import com.example.tea.catalog.application.port.out.SaveBrandPort;
import com.example.tea.catalog.domain.model.Brand;
import com.example.tea.global.exception.BizException;
import com.example.tea.global.exception.code.ErrorCode;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrandUseCaseTest {

    @Mock private LoadBrandPort loadBrandPort;
    @Mock private SaveBrandPort saveBrandPort;

    @InjectMocks private BrandApplicationService service;

    // ---- Test Fixtures 단위 테스트 실행 전, 일관된 테스트환경을 보장하기 위해 준비되는 상태

    private Brand testBrand(Long id, String name) {
        return new Brand(id, name, "Korea");
    }

    // ---- getBrands --------------------------------------------

    // 전체 브랜드 목록을 반환
    @Test
    void brandGetAllSuccessTest() {
        when(loadBrandPort.loadAll()).thenReturn(List.of(
                testBrand(1L, "Osulloc"),
                testBrand(2L, "TWG")));

        List<BrandResult> results = service.getBrands();

        assertThat(results).hasSize(2);
        assertThat(results.get(0).name()).isEqualTo("Osulloc");
    }

    // 브랜드가 없으면 빈 목록을 반환
    @Test
    void brandGetAllEmptyTest() {
        when(loadBrandPort.loadAll()).thenReturn(List.of());

        List<BrandResult> results = service.getBrands();

        assertThat(results).isEmpty();
    }

    // ---- createBrand ------------------------------------------

    // 정상적으로 생성하면 BrandResult
    @Test
    void brandCreateSuccessTest() {
        BrandCreateCommand command = new BrandCreateCommand("TWG", "Singapore");
        when(loadBrandPort.brandNameExists("TWG")).thenReturn(false);
        when(saveBrandPort.save(any(Brand.class))).thenReturn(testBrand(10L, "TWG"));

        BrandResult result = service.createBrand(command);

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.name()).isEqualTo("TWG");
    }

    // 이미 존재하는 이름이면 BrandAlreadyExistsException
    @Test
    void brandCreateDuplicateTest() {
        BrandCreateCommand command = new BrandCreateCommand("Osulloc", "Korea");
        when(loadBrandPort.brandNameExists("Osulloc")).thenReturn(true);

        assertThatThrownBy(() -> service.createBrand(command))
                .isInstanceOf(BrandAlreadyExistsException.class)
                .isInstanceOf(BizException.class)
                .hasMessageContaining("Osulloc");
    }

    // 중복 이름이면 ErrorCode가 BRAND_ALREADY_EXISTS
    @Test
    void brandCreateDuplicateErrorCodeTest() {
        BrandCreateCommand command = new BrandCreateCommand("Osulloc", "Korea");
        when(loadBrandPort.brandNameExists("Osulloc")).thenReturn(true);

        assertThatThrownBy(() -> service.createBrand(command))
                .isInstanceOf(BizException.class)
                .satisfies(ex -> assertThat(((BizException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.BRAND_ALREADY_EXISTS));
    }

    // ---- updateBrand ------------------------------------------

    // 정상적으로 수정하면 BrandResult
    @Test
    void brandUpdateSuccessTest() {
        BrandUpdateCommand command = new BrandUpdateCommand(1L, "Osulloc Updated", "Korea");
        Brand existing = testBrand(1L, "Osulloc");
        when(loadBrandPort.loadById(1L)).thenReturn(Optional.of(existing));
        when(loadBrandPort.brandNameExists("Osulloc Updated")).thenReturn(false);
        when(saveBrandPort.save(any(Brand.class))).thenAnswer(inv -> inv.getArgument(0));

        BrandResult result = service.updateBrand(command);

        assertThat(result.name()).isEqualTo("Osulloc Updated");
    }

    // 같은 이름으로 수정하면 중복검사를 통과한다
    @Test
    void brandUpdateSameNamePassTest() {
        BrandUpdateCommand command = new BrandUpdateCommand(1L, "Osulloc", "Japan");
        Brand existing = testBrand(1L, "Osulloc");
        when(loadBrandPort.loadById(1L)).thenReturn(Optional.of(existing));
        when(saveBrandPort.save(any(Brand.class))).thenAnswer(inv -> inv.getArgument(0));

        BrandResult result = service.updateBrand(command);

        assertThat(result.country()).isEqualTo("Japan");
    }

    // 존재하지 않는 브랜드면 BrandNotFoundException
    @Test
    void brandUpdateNotFoundTest() {
        BrandUpdateCommand command = new BrandUpdateCommand(99L, "NewName", "Korea");
        when(loadBrandPort.loadById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateBrand(command))
                .isInstanceOf(BrandNotFoundException.class)
                .isInstanceOf(BizException.class);
    }

    // 다른 브랜드의 이름과 중복이면 BrandAlreadyExistsException
    @Test
    void brandUpdateDuplicateTest() {
        BrandUpdateCommand command = new BrandUpdateCommand(1L, "TWG", "Korea");
        Brand existing = testBrand(1L, "Osulloc");
        when(loadBrandPort.loadById(1L)).thenReturn(Optional.of(existing));
        when(loadBrandPort.brandNameExists("TWG")).thenReturn(true);

        assertThatThrownBy(() -> service.updateBrand(command))
                .isInstanceOf(BrandAlreadyExistsException.class)
                .isInstanceOf(BizException.class);
    }

    // 브랜드 없을때 ErrorCode BRAND_NOT_FOUND
    @Test
    void brandUpdateNotFoundErrorCodeTest() {
        BrandUpdateCommand command = new BrandUpdateCommand(99L, "NewName", "Korea");
        when(loadBrandPort.loadById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateBrand(command))
                .isInstanceOf(BizException.class)
                .satisfies(ex -> assertThat(((BizException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.BRAND_NOT_FOUND));
    }
}
