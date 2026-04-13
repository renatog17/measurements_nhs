package com.nhst.medicoes.domain.dto;

import com.nhst.medicoes.domain.validation.ValidCpf;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateClientRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 120, message = "Name must be between 3 and 120 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "CPF is required")
        @ValidCpf
        String cpf
) {}