package com.nhst.medicoes.domain.bank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BankBoletoRegisterResponse(
        String status,          // REGISTERED | REJECTED
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