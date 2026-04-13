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
    private final PropertyRepository propertyRepository;
    private final ClientPropertyRepository clientPropertyRepository;

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

    public void assignProperty(Long clientId, Long propertyId) {

        if (clientPropertyRepository.existsByPropertyIdAndActiveTrue(propertyId)) {
            throw new IllegalStateException("Property already assigned");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalStateException("Client not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalStateException("Property not found"));

        ClientProperty relation = ClientProperty.builder()
                .client(client)
                .property(property)
                .build();

        clientPropertyRepository.save(relation);
    }
}