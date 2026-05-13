package com.nhst.medicoes.controller.dto.installation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateInstallation(

        Long meterId,
        @NotNull(message = "O cliente é obrigatório")
        Long clientId,
        @NotNull(message = "O imóvel é obrigatório")
        Long propertyId,
        @NotNull(message = "O leitor é obrigatório")
        Long readerId,
        BigDecimal volumeAtInstallation
) {
}