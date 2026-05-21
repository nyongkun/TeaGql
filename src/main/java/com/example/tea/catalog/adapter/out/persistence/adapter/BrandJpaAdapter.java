package com.example.tea.catalog.adapter.out.persistence.adapter;

import com.example.tea.catalog.adapter.out.persistence.converter.BrandJpaConverter;
import com.example.tea.catalog.adapter.out.persistence.entity.BrandJpaEntity;
import com.example.tea.catalog.adapter.out.persistence.repository.BrandJpaRepository;
import com.example.tea.catalog.application.port.out.LoadBrandPort;
import com.example.tea.catalog.application.port.out.SaveBrandPort;
import com.example.tea.catalog.domain.model.Brand;
import com.example.tea.global.exception.PersistenceException;
import com.example.tea.global.exception.code.ErrorCode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandJpaAdapter implements LoadBrandPort, SaveBrandPort {

    private final BrandJpaRepository brandJpaRepository;

    @Override
    public Optional<Brand> loadById(Long id) {
        try {
            return brandJpaRepository.findById(id).map(BrandJpaConverter::toDomain);
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    @Override
    public List<Brand> loadAll() {
        try {
            return brandJpaRepository.findAll().stream()
                    .map(BrandJpaConverter::toDomain)
                    .toList();
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    @Override
    public boolean brandNameExists(String name) {
        try {
            return brandJpaRepository.existsByNameIgnoreCase(name);
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    @Override
    public Brand save(Brand domain) {
        try {
            BrandJpaEntity entity = new BrandJpaEntity();
            entity.setId(domain.getId());
            entity.setName(domain.getName());
            entity.setCountry(domain.getCountry());
            return BrandJpaConverter.toDomain(brandJpaRepository.save(entity));
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }
}
