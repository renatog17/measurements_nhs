package com.nhst.medicoes.controller.dto.client;

import com.nhst.medicoes.domain.enums.PersonType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientFilter {

    private String name;
    private String email;
    private String document;
    private PersonType personType;
    private Boolean active;
}