package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.domain.dto.CreatePropertyRequest;
import com.nhst.medicoes.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Property create(@RequestBody @Valid CreatePropertyRequest req) {
        return propertyService.create(
                req.address(),
                req.city(),
                req.identifierCode()
        );
    }
}