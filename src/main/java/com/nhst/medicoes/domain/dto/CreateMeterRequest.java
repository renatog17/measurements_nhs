package com.nhst.medicoes.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMeterRequest(

        @NotBlank(message = "Serial number is required")
        @Size(max = 120, message = "Serial number must have at most 120 characters")
        String serialNumber,

        @NotBlank(message = "Meter type is required")
        @Size(max = 50, message = "Meter type must have at most 50 characters")
        String type
) {}