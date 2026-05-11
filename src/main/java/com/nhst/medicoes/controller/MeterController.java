package com.nhst.medicoes.controller;

import com.nhst.medicoes.controller.dto.meter.CreateMeterRequest;
import com.nhst.medicoes.controller.dto.meter.MeterFilter;
import com.nhst.medicoes.controller.dto.meter.MeterResponse;
import com.nhst.medicoes.service.MeterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
        meterService.create(req.serialNumber(), req.maxVolume(), req.actualVolume());
    }

    @GetMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public Page<MeterResponse> findAll(
            MeterFilter filter,
            @PageableDefault(size = 10, sort = "serialNumber")
            Pageable pageable
    ) {

        return meterService.findAll(filter, pageable);
    }
}