package com.example.tea.catalog.application.service;

import com.example.tea.catalog.application.dto.command.CreateBrandCommand;
import com.example.tea.catalog.application.dto.command.UpdateBrandCommand;
import com.example.tea.catalog.application.exception.BrandAlreadyExistsException;
import com.example.tea.catalog.application.exception.BrandNotFoundException;
import com.example.tea.catalog.application.port.in.BrandCommandUseCase;
import com.example.tea.catalog.application.port.in.BrandQueryUseCase;
import com.example.tea.catalog.application.port.out.BrandPort;
import com.example.tea.catalog.application.dto.result.BrandResult;
import com.example.tea.catalog.domain.model.Brand;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandService implements BrandQueryUseCase, BrandCommandUseCase {

    private final BrandPort brandPort;

    @Override
    @Transactional(readOnly = true)
    public List<BrandResult> getBrands() {
        List<Brand> brands = brandPort.findAll();
        List<BrandResult> results = new ArrayList<>();
        for (Brand brand : brands) {
            results.add(toResult(brand));
        }
        return results;
    }

    @Override
    @Transactional
    public BrandResult createBrand(CreateBrandCommand command) {
        if (brandPort.existsByName(command.name())) {
            throw new BrandAlreadyExistsException(command.name());
        }
        Brand brand = new Brand();
        brand.setName(command.name());
        brand.setCountry(command.country());
        return toResult(brandPort.save(brand));
    }

    @Override
    @Transactional
    public BrandResult updateBrand(UpdateBrandCommand command) {
        Brand brand = loadExistingBrand(command.id());
        if (!brand.getName().equalsIgnoreCase(command.name()) && brandPort.existsByName(command.name())) {
            throw new BrandAlreadyExistsException(command.name());
        }
        brand.setName(command.name());
        brand.setCountry(command.country());
        return toResult(brandPort.save(brand));
    }

    private Brand loadExistingBrand(Long id) {
        return brandPort.findById(id).orElseThrow(() -> new BrandNotFoundException(id));
    }

    private BrandResult toResult(Brand brand) {
        return new BrandResult(
                brand.getId(),
                brand.getName(),
                brand.getCountry());
    }
}
