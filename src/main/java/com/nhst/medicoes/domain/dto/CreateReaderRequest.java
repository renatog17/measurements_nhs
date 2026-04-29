package com.nhst.medicoes.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateReaderRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must have at most 255 characters")
        String name,

        @NotBlank(message = "Employee code is required")
        @Size(max = 100, message = "Employee code must have at most 100 characters")
        String employeeCode

) {}