package com.nhst.medicoes.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateMeasurementRequest(
        String meterSerialNumber,
        BigDecimal actualVolume,
        LocalDateTime measuredAt,
        Long readerId
) {}