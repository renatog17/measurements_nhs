package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.controller.dto.client.ClientFilter;
import com.nhst.medicoes.controller.dto.client.ClientResponse;
import com.nhst.medicoes.controller.dto.client.CreateClientRequest;
import com.nhst.medicoes.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid CreateClientRequest req) {
        Client client = service.create(req);
        ClientResponse response = ClientResponse.fromEntity(client);
        return ResponseEntity
                .created(URI.create("/client/" + response.getId()))
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public Page<ClientResponse> findAll(
            ClientFilter filter,
            @PageableDefault(size = 10, sort = "name")
            Pageable pageable
    ) {

        return service.findAll(filter, pageable);
    }
}