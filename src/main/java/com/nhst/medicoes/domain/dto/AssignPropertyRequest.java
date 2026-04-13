package com.nhst.medicoes.domain.dto;

public record AssignPropertyRequest(
        Long clientId,
        Long propertyId
) {}