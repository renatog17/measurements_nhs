package com.nhst.medicoes.controller.dto.address;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAddressRequest(

        @NotBlank(message = "Street is required")
        @Size(max = 255, message = "Street must have at most 255 characters")
        String street,

        @Size(max = 50, message = "Number must have at most 50 characters")
        String number,

        @Size(max = 255, message = "Complement must have at most 255 characters")
        String complement,

        @NotBlank(message = "Neighborhood is required")
        @Size(max = 120, message = "Neighborhood must have at most 120 characters")
        String neighborhood,

        @NotBlank(message = "City is required")
        @Size(max = 120, message = "City must have at most 120 characters")
        String city,

        @NotBlank(message = "State is required")
        @Size(max = 2, message = "State must have 2 characters")
        String state,

        @Size(max = 9, message = "Zip code must have at most 9 characters")
        String zipCode,

        Long propertyId

) {}