package com.nhst.medicoes.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TrocaMedidorRequest(
        Long idMedidor,
        Long idNovoMedidor,
        LocalDateTime horaDaTroca
) {
}
