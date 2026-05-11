package com.example.tea.catalog.adapter.out.persistence.adapter;

import com.example.tea.catalog.adapter.out.persistence.converter.TeaJpaConverter;
import com.example.tea.catalog.adapter.out.persistence.entity.BrandJpaEntity;
import com.example.tea.catalog.adapter.out.persistence.entity.TeaJpaEntity;
import com.example.tea.catalog.adapter.out.persistence.repository.BrandJpaRepository;
import com.example.tea.catalog.adapter.out.persistence.repository.TeaJpaRepository;
import com.example.tea.catalog.application.port.out.TeaPort;
import com.example.tea.catalog.domain.model.Tea;
import com.example.tea.catalog.domain.model.TeaType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeaJpaAdapter implements TeaPort {

    private final TeaJpaRepository teaJpaRepository;
    private final BrandJpaRepository brandJpaRepository;

    @Override
    public Optional<Tea> findById(Long id) {
        return teaJpaRepository.findById(id).map(TeaJpaConverter::toDomain);
    }

    @Override
    public List<Tea> findAll() {
        return teaJpaRepository.findAll().stream()
                .map(TeaJpaConverter::toDomain)
                .toList();
    }

    @Override
    public List<Tea> findByType(TeaType type) {
        return teaJpaRepository.findByType(type).stream()
                .map(TeaJpaConverter::toDomain)
                .toList();
    }

    @Override
    public List<Tea> findByBrandName(String brandName) {
        return teaJpaRepository.findByBrand_NameIgnoreCase(brandName).stream()
                .map(TeaJpaConverter::toDomain)
                .toList();
    }

    @Override
    public List<Tea> findByCaffeine(Boolean caffeine) {
        return teaJpaRepository.findByCaffeine(caffeine).stream()
                .map(TeaJpaConverter::toDomain)
                .toList();
    }

    @Override
    public Tea save(Tea domain) {
        BrandJpaEntity brandRef = brandJpaRepository.getReferenceById(domain.getBrand().getId());
        TeaJpaEntity entity = new TeaJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setType(domain.getType());
        entity.setBrand(brandRef);
        entity.setOriginCountry(domain.getOriginCountry());
        entity.setCaffeine(domain.getCaffeine());
        entity.setDescription(domain.getDescription());
        entity.setRating(domain.getRating());
        entity.setCreatedAt(domain.getCreatedAt());

        return TeaJpaConverter.toDomain(teaJpaRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        teaJpaRepository.deleteById(id);
    }
}
