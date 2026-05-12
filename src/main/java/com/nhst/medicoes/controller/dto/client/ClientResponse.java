package com.nhst.medicoes.controller.dto.client;

import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.domain.enums.PersonType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientResponse {

    private Long id;
    private String name;
    private String email;
    private String document;
    private PersonType personType;

    public static ClientResponse fromEntity(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .document(client.getDocument())
                .personType(client.getPersonType())
                .build();
    }
}