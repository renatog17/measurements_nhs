package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.controller.dto.client.ClientFilter;
import com.nhst.medicoes.controller.dto.client.ClientResponse;
import com.nhst.medicoes.controller.dto.client.CreateClientRequest;
import com.nhst.medicoes.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        Client client = new Client(req.name(), req.email(), req.cpf());

        return clientRepository.save(client);
    }

    public Client findById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalStateException("Client not found"));
    }

    public Page<ClientResponse> findAll(ClientFilter filter, Pageable pageable) {

        Page<Client> clients = clientRepository.findAll(
                ClientSpecification.withFilters(filter),
                pageable
        );

        return clients.map(ClientResponse::fromEntity);
    }
}