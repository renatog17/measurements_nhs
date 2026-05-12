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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<PropertyResponse> create(@RequestBody @Valid CreatePropertyRequest req) {

        Property property = propertyService.create(
                req.addressRequest(),
                req.parentPropertyId(),
                req.name(),
                req.type()
        );
        PropertyResponse propertyResponse = PropertyResponse.fromEntity(property);
        return ResponseEntity.ok(propertyResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public Page<PropertyResponse> findAll(
            PropertyFilter filter,
            @PageableDefault(size = 10)
            Pageable pageable
    ) {

        return propertyService.findAll(filter, pageable);
    }
}