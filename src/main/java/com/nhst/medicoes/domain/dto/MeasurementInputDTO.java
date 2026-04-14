package com.nhst.medicoes.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MeasurementInputDTO(
        Long meterPropertyId,
        BigDecimal value,
        LocalDateTime measuredAt
) {}