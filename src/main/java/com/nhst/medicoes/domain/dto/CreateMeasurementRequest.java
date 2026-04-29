package com.nhst.medicoes.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateMeasurementRequest(
        Long meterId,
        BigDecimal value,
        LocalDateTime measuredAt,
        Long readerId
) {}