package com.nhst.medicoes.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateMeterRequest(

        @NotBlank(message = "Serial number is required")
        @Size(max = 120, message = "Serial number must have at most 120 characters")
        String serialNumber,

        @NotNull(message = "Max value is required")
        @Positive(message = "Max value must be greater than zero")
        @Digits(integer = 9, fraction = 3, message = "Max value must have up to 9 integer digits and 3 decimal places")
        BigDecimal maxValue
) {}