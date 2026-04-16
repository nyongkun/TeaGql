package com.example.tea.catalog.adapter.out.persistence;

import com.example.tea.catalog.domain.model.TeaType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeaJpaRepository extends JpaRepository<TeaJpaEntity, Long> {

    List<TeaJpaEntity> findByType(TeaType type);

    List<TeaJpaEntity> findByBrand_NameIgnoreCase(String brandName);

    List<TeaJpaEntity> findByCaffeine(Boolean caffeine);
}
