package com.example.tea.catalog.adapter.out.persistence.repository;

import com.example.tea.catalog.adapter.out.persistence.entity.BrandJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandJpaRepository extends JpaRepository<BrandJpaEntity, Long> {

    Optional<BrandJpaEntity> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
