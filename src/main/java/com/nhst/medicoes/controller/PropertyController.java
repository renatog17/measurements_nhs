package com.nhst.medicoes.controller;

import com.nhst.medicoes.controller.dto.property.PropertyFilter;
import com.nhst.medicoes.controller.dto.property.PropertyResponse;
import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.controller.dto.property.CreatePropertyRequest;
import com.nhst.medicoes.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public Property create(@RequestBody @Valid CreatePropertyRequest req) {
        return propertyService.create(
                req.address(),
                req.city(),
                req.identifierCode(),
                req.parentPropertyId()
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public Page<PropertyResponse> findAll(
            PropertyFilter filter,
            @PageableDefault(size = 10, sort = "city")
            Pageable pageable
    ) {

        return propertyService.findAll(filter, pageable);
    }
}