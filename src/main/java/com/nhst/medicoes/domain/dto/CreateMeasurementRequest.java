package com.nhst.medicoes.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateMeasurementRequest(
        Long meterId,
        BigDecimal actualVolume,
        LocalDateTime measuredAt,
        Long readerId
) {}