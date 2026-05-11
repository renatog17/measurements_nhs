package com.nhst.medicoes.controller.dto.property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyFilter {

    private String city;
    private String identifierCode;
    private Boolean active;
}