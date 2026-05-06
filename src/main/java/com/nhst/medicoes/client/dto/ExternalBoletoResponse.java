package com.nhst.medicoes.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExternalBoletoResponse(
        String status,
        String nossoNumero,
        String documentNumber,
        String barcode,
        String digitableLine,
        BigDecimal amount,
        LocalDate dueDate,
        String pdfUrl,
        String errorCode,
        String message
) {}