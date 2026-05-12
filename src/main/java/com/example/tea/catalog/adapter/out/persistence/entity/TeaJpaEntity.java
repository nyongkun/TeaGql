package com.example.tea.catalog.adapter.out.persistence.entity;

import com.example.tea.catalog.domain.model.TeaType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "teas")
@Getter
@Setter
public class TeaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeaType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandJpaEntity brand;

    private String originCountry;

    @Column(nullable = false)
    private Boolean caffeine;

    @Column(length = 1000)
    private String description;

    private Float rating;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public TeaJpaEntity() {
    }
}
