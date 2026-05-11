package com.example.tea.catalog.adapter.out.persistence.adapter;

import com.example.tea.catalog.adapter.out.persistence.converter.BrandJpaConverter;
import com.example.tea.catalog.adapter.out.persistence.entity.BrandJpaEntity;
import com.example.tea.catalog.adapter.out.persistence.repository.BrandJpaRepository;
import com.example.tea.catalog.application.port.out.BrandPort;
import com.example.tea.catalog.domain.model.Brand;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandJpaAdapter implements BrandPort {

    private final BrandJpaRepository brandJpaRepository;

    @Override
    public Optional<Brand> findById(Long id) {
        return brandJpaRepository.findById(id).map(BrandJpaConverter::toDomain);
    }

    @Override
    public List<Brand> findAll() {
        return brandJpaRepository.findAll().stream()
                .map(BrandJpaConverter::toDomain)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return brandJpaRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public Brand save(Brand domain) {
        BrandJpaEntity entity = new BrandJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setCountry(domain.getCountry());
        return BrandJpaConverter.toDomain(brandJpaRepository.save(entity));
    }
}
