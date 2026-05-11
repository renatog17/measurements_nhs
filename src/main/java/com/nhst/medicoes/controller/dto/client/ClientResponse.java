package com.nhst.medicoes.controller.dto.client;


import com.nhst.medicoes.domain.Client;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientResponse {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private boolean active;

    public static ClientResponse fromEntity(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .cpf(client.getCpf())
                .active(client.isActive())
                .build();
    }
}