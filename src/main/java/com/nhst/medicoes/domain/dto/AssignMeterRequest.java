package com.nhst.medicoes.domain.dto;

import jakarta.validation.constraints.NotNull;

public record AssignMeterRequest(

        @NotNull(message = "Meter id is required")
        Long meterId,

        @NotNull(message = "Property id is required")
        Long propertyId
) {}