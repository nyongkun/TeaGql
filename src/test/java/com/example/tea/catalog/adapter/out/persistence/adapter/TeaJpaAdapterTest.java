package com.example.tea.catalog.adapter.out.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.example.tea.catalog.adapter.out.persistence.repository.BrandJpaRepository;
import com.example.tea.catalog.adapter.out.persistence.repository.TeaJpaRepository;
import com.example.tea.catalog.domain.model.Brand;
import com.example.tea.catalog.domain.model.Tea;
import com.example.tea.catalog.domain.model.TeaType;
import com.example.tea.global.exception.PersistenceException;
import com.example.tea.global.exception.code.ErrorCode;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
class TeaJpaAdapterTest {

    @Mock private TeaJpaRepository teaJpaRepository;
    @Mock private BrandJpaRepository brandJpaRepository;
    private TeaJpaAdapter teaJpaAdapter;

    @BeforeEach
    void setUp() {
        teaJpaAdapter = new TeaJpaAdapter(teaJpaRepository, brandJpaRepository);
    }

    // save DataAccessException 발생 시 PersistenceException으로 변환한다
    @Test
    void teaSaveThrowsPersistenceExceptionOnDataAccessExceptionTest() {
        Brand brand = new Brand(1L, "Osulloc", "Korea");
        Tea tea = new Tea(null, "Green Tea", TeaType.GREEN, brand, "Korea", false, null, null, LocalDateTime.now());
        when(brandJpaRepository.getReferenceById(any())).thenThrow(new DataIntegrityViolationException("constraint"));

        assertThatThrownBy(() -> teaJpaAdapter.save(tea))
                .isInstanceOf(PersistenceException.class)
                .satisfies(ex -> assertThat(((PersistenceException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.PERSISTENCE_ERROR));
    }

    // delete DataAccessException 발생 시 PersistenceException으로 변환한다
    @Test
    void teaDeleteThrowsPersistenceExceptionOnDataAccessExceptionTest() {
        doThrow(new DataIntegrityViolationException("constraint")).when(teaJpaRepository).deleteById(1L);

        assertThatThrownBy(() -> teaJpaAdapter.delete(1L))
                .isInstanceOf(PersistenceException.class)
                .satisfies(ex -> assertThat(((PersistenceException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.PERSISTENCE_ERROR));
    }
}
