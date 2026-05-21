package com.example.tea.catalog.adapter.out.persistence.adapter;

import com.example.tea.catalog.adapter.out.persistence.converter.TeaJpaConverter;
import com.example.tea.catalog.adapter.out.persistence.entity.BrandJpaEntity;
import com.example.tea.catalog.adapter.out.persistence.entity.TeaJpaEntity;
import com.example.tea.catalog.adapter.out.persistence.repository.BrandJpaRepository;
import com.example.tea.catalog.adapter.out.persistence.repository.TeaJpaRepository;
import com.example.tea.catalog.application.port.out.LoadTeaPort;
import com.example.tea.catalog.application.port.out.SaveTeaPort;
import com.example.tea.catalog.domain.model.Tea;
import com.example.tea.catalog.domain.model.TeaType;
import com.example.tea.global.exception.PersistenceException;
import com.example.tea.global.exception.code.ErrorCode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeaJpaAdapter implements LoadTeaPort, SaveTeaPort {

    private final TeaJpaRepository teaJpaRepository;
    private final BrandJpaRepository brandJpaRepository;

    @Override
    public Optional<Tea> loadById(Long id) {
        try {
            return teaJpaRepository.findById(id).map(TeaJpaConverter::toDomain);
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    @Override
    public List<Tea> loadAll() {
        try {
            return teaJpaRepository.findAll().stream()
                    .map(TeaJpaConverter::toDomain)
                    .toList();
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    @Override
    public List<Tea> loadByType(TeaType type) {
        try {
            return teaJpaRepository.findByType(type).stream()
                    .map(TeaJpaConverter::toDomain)
                    .toList();
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    @Override
    public List<Tea> loadByBrandName(String brandName) {
        try {
            return teaJpaRepository.findByBrand_NameIgnoreCase(brandName).stream()
                    .map(TeaJpaConverter::toDomain)
                    .toList();
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    @Override
    public List<Tea> loadByCaffeine(Boolean caffeine) {
        try {
            return teaJpaRepository.findByCaffeine(caffeine).stream()
                    .map(TeaJpaConverter::toDomain)
                    .toList();
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    @Override
    public Tea save(Tea domain) {
        try {
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
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            teaJpaRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new PersistenceException(ErrorCode.PERSISTENCE_ERROR, e);
        }
    }
}
