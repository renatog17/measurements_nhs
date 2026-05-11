package com.nhst.medicoes.controller;

import com.nhst.medicoes.controller.dto.CreateMeasurementRequest;
import com.nhst.medicoes.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping()
    public ResponseEntity<Void> receiveMeasurement(
            @RequestBody CreateMeasurementRequest createMeasurementRequest
    ) throws Exception {
        measurementService.createMeasurement(createMeasurementRequest.measuredAt(),
                createMeasurementRequest.meterSerialNumber(),
                createMeasurementRequest.actualVolume(),
                createMeasurementRequest.readerId());

        return ResponseEntity.ok().build();
    }


}