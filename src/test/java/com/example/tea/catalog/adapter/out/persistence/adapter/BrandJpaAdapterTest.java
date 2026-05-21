package com.example.tea.catalog.adapter.out.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.example.tea.catalog.adapter.out.persistence.repository.BrandJpaRepository;
import com.example.tea.catalog.domain.model.Brand;
import com.example.tea.global.exception.PersistenceException;
import com.example.tea.global.exception.code.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
class BrandJpaAdapterTest {

    @Mock private BrandJpaRepository brandJpaRepository;
    private BrandJpaAdapter brandJpaAdapter;

    @BeforeEach
    void setUp() {
        brandJpaAdapter = new BrandJpaAdapter(brandJpaRepository);
    }

    // DataAccessException 발생 시 PersistenceException으로 변환한다
    @Test
    void brandSaveThrowsPersistenceExceptionOnDataAccessExceptionTest() {
        Brand brand = new Brand(null, "Osulloc", "Korea");
        when(brandJpaRepository.save(any())).thenThrow(new DataIntegrityViolationException("constraint"));

        assertThatThrownBy(() -> brandJpaAdapter.save(brand))
                .isInstanceOf(PersistenceException.class)
                .satisfies(ex -> assertThat(((PersistenceException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.PERSISTENCE_ERROR));
    }

    // loadAll DataAccessException 발생 시 PersistenceException으로 변환한다
    @Test
    void brandLoadAllThrowsPersistenceExceptionOnDataAccessExceptionTest() {
        when(brandJpaRepository.findAll()).thenThrow(new DataIntegrityViolationException("connection"));

        assertThatThrownBy(() -> brandJpaAdapter.loadAll())
                .isInstanceOf(PersistenceException.class)
                .satisfies(ex -> assertThat(((PersistenceException) ex).getErrorCode())
                        .isEqualTo(ErrorCode.PERSISTENCE_ERROR));
    }
}
