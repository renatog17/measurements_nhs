package com.nhst.medicoes.domain.dto;

import java.math.BigDecimal;

public record CreateInstallation(
        Long meterId,
        Long clientId,
        Long propertyId,
        BigDecimal volumeAtAssigned,
        Long readerId
) {
}
