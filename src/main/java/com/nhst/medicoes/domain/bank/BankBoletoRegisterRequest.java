package com.nhst.medicoes.domain.bank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BankBoletoRegisterRequest(
        Beneficiary beneficiary,
        Payer payer,
        Title title,
        Instructions instructions
) {

    public record Beneficiary(
            String name,
            String document, // CPF/CNPJ
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
            String documentNumber, // invoice id
            BigDecimal amount,
            LocalDate dueDate,
            LocalDate issueDate
    ) {}

    public record Instructions(
            BigDecimal finePercent,     // ex: 2.0 (%)
            BigDecimal interestPerDay   // ex: 0.033 (% ao dia)
    ) {}
}