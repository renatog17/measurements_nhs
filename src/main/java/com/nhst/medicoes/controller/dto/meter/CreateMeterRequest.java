package com.nhst.medicoes.controller.dto.meter;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateMeterRequest(

        @NotBlank(message = "Serial number is required")
        @Size(max = 120, message = "Serial number must have at most 120 characters")
        String serialNumber,

        @NotNull(message = "Max value is required")
        @Positive(message = "Max value must be greater than zero")
        @Digits(integer = 9, fraction = 3, message = "Max volume must have up to 9 integer digits and 3 decimal places")
        BigDecimal maxVolume
) {}