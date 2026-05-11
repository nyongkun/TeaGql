package com.example.tea.catalog.application.service;

import com.example.tea.catalog.application.dto.command.CreateTeaCommand;
import com.example.tea.catalog.application.dto.command.UpdateTeaCommand;
import com.example.tea.catalog.application.exception.BrandNotFoundException;
import com.example.tea.catalog.application.exception.TeaNotFoundException;
import com.example.tea.catalog.application.port.in.TeaCommandUseCase;
import com.example.tea.catalog.application.port.in.TeaQueryUseCase;
import com.example.tea.catalog.application.dto.query.GetTeaQuery;
import com.example.tea.catalog.application.dto.query.GetTeasByCaffeineQuery;
import com.example.tea.catalog.application.dto.query.GetTeasByBrandQuery;
import com.example.tea.catalog.application.dto.query.GetTeasByTypeQuery;
import com.example.tea.catalog.application.port.out.BrandPort;
import com.example.tea.catalog.application.port.out.TeaPort;
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
public class TeaService implements TeaQueryUseCase, TeaCommandUseCase {

    private final TeaPort teaPort;
    private final BrandPort brandPort;

    @Override
    @Transactional(readOnly = true)
    public List<TeaResult> getTeas() {
        List<Tea> teas = teaPort.findAll();
        List<TeaResult> results = new ArrayList<>();
        for (Tea tea : teas) {
            results.add(toResult(tea));
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public TeaResult getTea(GetTeaQuery query) {
        return toResult(loadExistingTea(query.id()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeaResult> getTeasByType(GetTeasByTypeQuery query) {
        List<Tea> teas = teaPort.findByType(query.type());
        List<TeaResult> results = new ArrayList<>();
        for (Tea tea : teas) {
            results.add(toResult(tea));
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeaResult> getTeasByBrand(GetTeasByBrandQuery query) {
        List<Tea> teas = teaPort.findByBrandName(query.brandName());
        List<TeaResult> results = new ArrayList<>();
        for (Tea tea : teas) {
            results.add(toResult(tea));
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeaResult> getTeasByCaffeine(GetTeasByCaffeineQuery query) {
        List<Tea> teas = teaPort.findByCaffeine(query.caffeine());
        List<TeaResult> results = new ArrayList<>();
        for (Tea tea : teas) {
            results.add(toResult(tea));
        }
        return results;
    }

    @Override
    @Transactional
    public TeaResult createTea(CreateTeaCommand command) {
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
        return toResult(teaPort.save(tea));
    }

    @Override
    @Transactional
    public TeaResult updateTea(UpdateTeaCommand command) {
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
        return toResult(teaPort.save(tea));
    }

    @Override
    @Transactional
    public boolean deleteTea(Long id) {
        loadExistingTea(id);
        teaPort.delete(id);
        return true;
    }

    private Tea loadExistingTea(Long id) {
        return teaPort.findById(id).orElseThrow(() -> new TeaNotFoundException(id));
    }

    private Brand loadExistingBrand(Long id) {
        return brandPort.findById(id).orElseThrow(() -> new BrandNotFoundException(id));
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
