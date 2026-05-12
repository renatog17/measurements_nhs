package com.nhst.medicoes.controller.dto.installation;

import java.math.BigDecimal;

public record CreateInstallation(
        Long meterId,
        Long clientId,
        Long propertyId,
        BigDecimal volumeAtAssigned,
        Long readerId
) {
}
