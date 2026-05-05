package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.dto.AssignMeterRequest;
import com.nhst.medicoes.service.MeterPropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meterproperty")
@RequiredArgsConstructor
public class MeterPropertyController {

    private final MeterPropertyService meterPropertyService;

    @PostMapping("/associate")
    @PreAuthorize("hasRole('OPERATOR')")
    public void assign(@RequestBody @Valid AssignMeterRequest req) throws Exception {
        meterPropertyService.associateMeterToProperty(req.meterId(), req.propertyId(), req.readerId());
    }

    @PostMapping("/unlink")
    @PreAuthorize("hasRole('OPERATOR')")
    public void unlink(@RequestBody @Valid AssignMeterRequest req){
        meterPropertyService.unlinkMeterFromProperty(req.meterId(), req.propertyId());
    }

}
