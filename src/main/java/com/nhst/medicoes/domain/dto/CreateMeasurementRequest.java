package com.nhst.medicoes.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateMeasurementRequest(
        String meterSerialNumber,
        BigDecimal actualVolume,
        LocalDateTime measuredAt,
        Long readerId
) {}