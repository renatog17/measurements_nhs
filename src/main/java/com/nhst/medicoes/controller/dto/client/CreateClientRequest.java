package com.nhst.medicoes.controller.dto.client;

import com.nhst.medicoes.controller.dto.address.CreateAddressRequest;
import com.nhst.medicoes.domain.enums.PersonType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateClientRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 120, message = "Name must be between 3 and 120 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Document is required")
        @Size(min = 11, max = 20, message = "Document must be valid (CPF or CNPJ)")
        String document,

        @NotNull(message = "Person type is required")
        PersonType personType,

        @NotNull(message = "Address is required")
        CreateAddressRequest addressRequest

) {}