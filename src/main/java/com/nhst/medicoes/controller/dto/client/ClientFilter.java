package com.nhst.medicoes.controller.dto.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientFilter {

    private String name;
    private String email;
    private String cpf;
    private Boolean active;
}