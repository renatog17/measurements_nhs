package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.dto.AssignMeterRequest;
import com.nhst.medicoes.domain.dto.CreateMeterRequest;
import com.nhst.medicoes.service.MeterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meters")
@RequiredArgsConstructor
public class MeterController {

    private final MeterService meterService;

    @PostMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public void create(@RequestBody @Valid CreateMeterRequest req) {
        meterService.create(req.serialNumber());
    }

    @PostMapping("/assign-property")
    @PreAuthorize("hasRole('OPERATOR')")
    public void assign(@RequestBody @Valid AssignMeterRequest req) {
        meterService.assignToProperty(req.meterId(), req.propertyId());
    }
}