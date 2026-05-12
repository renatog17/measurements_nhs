package com.nhst.medicoes.controller.dto.property;

import com.nhst.medicoes.domain.enums.PropertyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyFilter {

    private String identifierCode;
    private Boolean active;
    private Long parentPropertyId;
    private PropertyType type;
}