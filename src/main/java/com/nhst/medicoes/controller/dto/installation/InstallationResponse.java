package com.nhst.medicoes.controller.dto.installation;

import com.nhst.medicoes.domain.Installation;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class InstallationResponse {

    private Long id;

    private Long clientId;
    private String clientName;
    private Long propertyId;
    private String propertyIdentifierCode;
    private String propertyName;
    private Long meterId;
    private String meterSerialNumber;
    private boolean active;
    private LocalDateTime assignedAt;
    private LocalDateTime unassignedAt;
    public static InstallationResponse fromEntity(Installation installation) {

        return InstallationResponse.builder()
                .id(installation.getId())

                .clientId(installation.getClient().getId())
                .clientName(installation.getClient().getName())

                .propertyId(installation.getProperty().getId())
                .propertyIdentifierCode(
                        installation.getProperty().getIdentifierCode()
                )
                .propertyName(
                        installation.getProperty().getName()
                )

                .meterId(
                        installation.getMeter() != null
                                ? installation.getMeter().getId()
                                : null
                )
                .meterSerialNumber(
                        installation.getMeter() != null
                                ? installation.getMeter().getSerialNumber()
                                : null
                )

                .active(installation.isActive())

                .assignedAt(installation.getAssignedAt())
                .unassignedAt(installation.getUnassignedAt())

                .build();
    }
}