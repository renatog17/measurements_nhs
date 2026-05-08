package com.nhst.medicoes.client.dto;

import com.nhst.medicoes.domain.enums.BankSlipStatus;

import java.time.LocalDateTime;

public record BoletoStatusResponse(

        String nossoNumero,
        BankSlipStatus status,
        LocalDateTime paidAt

) {
}