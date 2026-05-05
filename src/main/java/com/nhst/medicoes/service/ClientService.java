package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.domain.ClientProperty;
import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.domain.dto.CreateClientRequest;
import com.nhst.medicoes.repository.ClientPropertyRepository;
import com.nhst.medicoes.repository.ClientRepository;
import com.nhst.medicoes.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client create(CreateClientRequest req) {

        if (clientRepository.existsByCpf(req.cpf())) {
            throw new IllegalStateException("CPF already registered");
        }

        if (clientRepository.existsByEmail(req.email())) {
            throw new IllegalStateException("Email already registered");
        }

        Client client = Client.builder()
                .name(req.name())
                .email(req.email())
                .cpf(req.cpf())
                .build();

        return clientRepository.save(client);
    }

    public Client findById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalStateException("Client not found"));
    }
}