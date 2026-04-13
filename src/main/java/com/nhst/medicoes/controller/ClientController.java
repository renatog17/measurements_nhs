package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.domain.dto.AssignPropertyRequest;
import com.nhst.medicoes.domain.dto.CreateClientRequest;
import com.nhst.medicoes.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Client create(@RequestBody @Valid CreateClientRequest req) {
        return service.create(req);
    }

    @PostMapping("/assign-property")
    @PreAuthorize("hasRole('ADMIN')")
    public void assign(@RequestBody AssignPropertyRequest req) {
        service.assignProperty(req.clientId(), req.propertyId());
    }
}