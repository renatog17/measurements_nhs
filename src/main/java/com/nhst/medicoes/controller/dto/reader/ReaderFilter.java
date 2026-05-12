package com.nhst.medicoes.controller.dto.reader;
import com.nhst.medicoes.controller.dto.client.ClientResponse;
import com.nhst.medicoes.domain.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReaderFilter {

    private String name;
    private String employeeCode;

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
