package com.nhst.medicoes.auth.dto;

import com.nhst.medicoes.auth.UserRole;

public record CreateUserRequest(
        String name,
        String email,
        String password,
        UserRole role
) {}