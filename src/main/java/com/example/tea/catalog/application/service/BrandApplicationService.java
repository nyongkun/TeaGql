package com.example.tea.catalog.application.service;

import com.example.tea.catalog.application.dto.command.BrandCreateCommand;
import com.example.tea.catalog.application.dto.command.BrandUpdateCommand;
import com.example.tea.catalog.application.exception.BrandAlreadyExistsException;
import com.example.tea.catalog.application.exception.BrandNotFoundException;
import com.example.tea.catalog.application.port.in.BrandCommandUseCase;
import com.example.tea.catalog.application.port.in.BrandQueryUseCase;
import com.example.tea.catalog.application.port.out.LoadBrandPort;
import com.example.tea.catalog.application.port.out.SaveBrandPort;
import com.example.tea.catalog.application.dto.result.BrandResult;
import com.example.tea.catalog.domain.model.Brand;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandApplicationService implements BrandQueryUseCase, BrandCommandUseCase {

    private final LoadBrandPort loadBrandPort;
    private final SaveBrandPort saveBrandPort;

    @Override
    @Transactional(readOnly = true)
    public List<BrandResult> getBrands() {
        List<Brand> brands = loadBrandPort.loadAll();
        List<BrandResult> results = new ArrayList<>();
        for (Brand brand : brands) {
            results.add(toResult(brand));
        }
        return results;
    }

    @Override
    @Transactional
    public BrandResult createBrand(BrandCreateCommand command) {
        if (loadBrandPort.brandNameExists(command.name())) {
            throw new BrandAlreadyExistsException(command.name());
        }
        Brand brand = new Brand();
        brand.setName(command.name());
        brand.setCountry(command.country());
        return toResult(saveBrandPort.save(brand));
    }

    @Override
    @Transactional
    public BrandResult updateBrand(BrandUpdateCommand command) {
        Brand brand = loadExistingBrand(command.id());
        if (!brand.getName().equalsIgnoreCase(command.name()) && loadBrandPort.brandNameExists(command.name())) {
            throw new BrandAlreadyExistsException(command.name());
        }
        brand.setName(command.name());
        brand.setCountry(command.country());
        return toResult(saveBrandPort.save(brand));
    }

    private Brand loadExistingBrand(Long id) {
        return loadBrandPort.loadById(id).orElseThrow(() -> new BrandNotFoundException(id));
    }

    private BrandResult toResult(Brand brand) {
        return new BrandResult(
                brand.getId(),
                brand.getName(),
                brand.getCountry());
    }
}
