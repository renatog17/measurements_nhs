package com.nhst.medicoes.controller.dto.property;

import com.nhst.medicoes.domain.Property;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PropertyResponse {

    private Long id;
    private String identifierCode;
    private boolean active;

    private Long parentPropertyId;
    private String parentPropertyIdentifierCode;

    public static PropertyResponse fromEntity(Property property) {

        return PropertyResponse.builder()
                .id(property.getId())
                .identifierCode(property.getIdentifierCode())
                .active(property.isActive())

                .parentPropertyId(
                        property.getParentProperty() != null
                                ? property.getParentProperty().getId()
                                : null
                )
                .parentPropertyIdentifierCode(
                        property.getParentProperty() != null
                                ? property.getParentProperty().getIdentifierCode()
                                : null
                )
                .build();
    }
}