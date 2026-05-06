package com.nhst.medicoes.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExternalBoletoRequest(
        Beneficiary beneficiary,
        Payer payer,
        Title title,
        Charges charges
) {
    public record Beneficiary(
            String name,
            String document,
            String agency,
            String account
    ) {}

    public record Payer(
            String name,
            String document,
            Address address
    ) {}

    public record Address(
            String street,
            String number,
            String city,
            String state,
            String zipCode
    ) {}

    public record Title(
            String documentNumber,
            BigDecimal amount,
            LocalDate dueDate,
            LocalDate issueDate
    ) {}

    public record Charges(
            BigDecimal finePercent,
            BigDecimal interestPerDay
    ) {}
}