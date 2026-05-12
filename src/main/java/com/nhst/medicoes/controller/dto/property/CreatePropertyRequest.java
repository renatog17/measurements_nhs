package com.nhst.medicoes.controller.dto.property;

import com.nhst.medicoes.controller.dto.address.CreateAddressRequest;
import com.nhst.medicoes.domain.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePropertyRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 120, message = "Name must have at most 120 characters")
        String name,

        @NotNull(message = "Type is required")
        PropertyType type,

        @NotNull(message = "Address is required")
        CreateAddressRequest addressRequest,

        Long parentPropertyId

) {}