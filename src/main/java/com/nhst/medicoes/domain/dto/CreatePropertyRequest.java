package com.nhst.medicoes.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePropertyRequest(

        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address must have at most 255 characters")
        String address,

        @NotBlank(message = "City is required")
        @Size(max = 120, message = "City must have at most 120 characters")
        String city,

        @NotBlank(message = "Identifier code is required")
        @Size(max = 120, message = "Identifier code must have at most 120 characters")
        String identifierCode
) {}