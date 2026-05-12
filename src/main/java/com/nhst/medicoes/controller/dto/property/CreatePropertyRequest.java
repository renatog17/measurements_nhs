package com.nhst.medicoes.controller.dto.property;

import com.nhst.medicoes.controller.dto.address.CreateAddressRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePropertyRequest(

        @NotBlank(message = "Identifier code is required")
        @Size(max = 120, message = "Identifier code must have at most 120 characters")
        String identifierCode,

        @NotNull(message = "Address is required")
        CreateAddressRequest addressRequest,
        String name,

        Long parentPropertyId
) {}