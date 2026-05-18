package com.nhst.medicoes.service;

import com.nhst.medicoes.clock.AppTime;
import com.nhst.medicoes.domain.Address;
import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.controller.dto.client.ClientFilter;
import com.nhst.medicoes.controller.dto.client.ClientResponse;
import com.nhst.medicoes.controller.dto.client.CreateClientRequest;
import com.nhst.medicoes.repository.AddressRepository;
import com.nhst.medicoes.repository.ClientRepository;
import com.nhst.medicoes.service.specification.ClientSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final AppTime appTime;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public Client create(CreateClientRequest req) {

        if (clientRepository.existsByDocumentOrEmail(req.document(), req.email())) {
            throw new IllegalStateException("CPF or email already registered");
        }

        Address address = new Address();

        address.setStreet(req.addressRequest().street());
        address.setNumber(req.addressRequest().number());
        address.setComplement(req.addressRequest().complement());
        address.setNeighborhood(req.addressRequest().neighborhood());
        address.setCity(req.addressRequest().city());
        address.setState(req.addressRequest().state());
        address.setZipCode(req.addressRequest().zipCode());

        addressRepository.saveAndFlush(address);

        Client client = new Client(req.name(), req.email(), req.document(), req.personType(), address);
        client.setCreatedAt(appTime.nowDateTime());
        client.setUpdatedAt(appTime.nowDateTime());

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