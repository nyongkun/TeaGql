package com.example.tea.catalog.application.service;

import com.example.tea.catalog.application.dto.command.TeaCreateCommand;
import com.example.tea.catalog.application.dto.command.TeaUpdateCommand;
import com.example.tea.catalog.application.exception.BrandNotFoundException;
import com.example.tea.catalog.application.exception.TeaNotFoundException;
import com.example.tea.catalog.application.port.in.TeaCommandUseCase;
import com.example.tea.catalog.application.port.in.TeaQueryUseCase;
import com.example.tea.catalog.application.dto.query.TeaByIdQuery;
import com.example.tea.catalog.application.dto.query.TeaByCaffeineQuery;
import com.example.tea.catalog.application.dto.query.TeaByBrandQuery;
import com.example.tea.catalog.application.dto.query.TeaByTypeQuery;
import com.example.tea.catalog.application.port.out.LoadBrandPort;
import com.example.tea.catalog.application.port.out.LoadTeaPort;
import com.example.tea.catalog.application.port.out.SaveTeaPort;
import com.example.tea.catalog.application.dto.result.BrandResult;
import com.example.tea.catalog.application.dto.result.TeaResult;
import com.example.tea.catalog.domain.model.Brand;
import com.example.tea.catalog.domain.model.Tea;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeaApplicationService implements TeaQueryUseCase, TeaCommandUseCase {

    private final LoadTeaPort loadTeaPort;
    private final SaveTeaPort saveTeaPort;
    private final LoadBrandPort loadBrandPort;

    @Override
    @Transactional(readOnly = true)
    public List<TeaResult> getTeas() {
        List<Tea> teas = loadTeaPort.loadAll();
        List<TeaResult> results = new ArrayList<>();
        for (Tea tea : teas) {
            results.add(toResult(tea));
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public TeaResult getTea(TeaByIdQuery query) {
        return toResult(loadExistingTea(query.id()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeaResult> getTeasByType(TeaByTypeQuery query) {
        List<Tea> teas = loadTeaPort.loadByType(query.type());
        List<TeaResult> results = new ArrayList<>();
        for (Tea tea : teas) {
            results.add(toResult(tea));
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeaResult> getTeasByBrand(TeaByBrandQuery query) {
        List<Tea> teas = loadTeaPort.loadByBrandName(query.brandName());
        List<TeaResult> results = new ArrayList<>();
        for (Tea tea : teas) {
            results.add(toResult(tea));
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeaResult> getTeasByCaffeine(TeaByCaffeineQuery query) {
        List<Tea> teas = loadTeaPort.loadByCaffeine(query.caffeine());
        List<TeaResult> results = new ArrayList<>();
        for (Tea tea : teas) {
            results.add(toResult(tea));
        }
        return results;
    }

    @Override
    @Transactional
    public TeaResult createTea(TeaCreateCommand command) {
        Brand brand = loadExistingBrand(command.brandId());
        Tea tea = new Tea();
        tea.setName(command.name());
        tea.setType(command.type());
        tea.setBrand(brand);
        tea.setOriginCountry(command.originCountry());
        tea.setCaffeine(command.caffeine());
        tea.setDescription(command.description());
        tea.setRating(command.rating());
        tea.setCreatedAt(LocalDateTime.now());
        return toResult(saveTeaPort.save(tea));
    }

    @Override
    @Transactional
    public TeaResult updateTea(TeaUpdateCommand command) {
        Tea tea = loadExistingTea(command.id());
        Brand brand = loadExistingBrand(command.brandId());
        tea.update(
                command.name(),
                command.type(),
                brand,
                command.originCountry(),
                command.caffeine(),
                command.description(),
                command.rating()
        );
        return toResult(saveTeaPort.save(tea));
    }

    @Override
    @Transactional
    public boolean deleteTea(Long id) {
        loadExistingTea(id);
        saveTeaPort.delete(id);
        return true;
    }

    private Tea loadExistingTea(Long id) {
        return loadTeaPort.loadById(id).orElseThrow(() -> new TeaNotFoundException(id));
    }

    private Brand loadExistingBrand(Long id) {
        return loadBrandPort.loadById(id).orElseThrow(() -> new BrandNotFoundException(id));
    }

    private TeaResult toResult(Tea tea) {
        BrandResult brandResult = new BrandResult(
                tea.getBrand().getId(),
                tea.getBrand().getName(),
                tea.getBrand().getCountry()
        );
        return new TeaResult(
                tea.getId(),
                tea.getName(),
                tea.getType(),
                brandResult,
                tea.getOriginCountry(),
                tea.getCaffeine(),
                tea.getDescription(),
                tea.getRating(),
                tea.getCreatedAt()
        );
    }
}
