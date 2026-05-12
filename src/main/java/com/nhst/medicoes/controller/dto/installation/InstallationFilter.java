package com.nhst.medicoes.controller.dto.installation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstallationFilter {

    private Long clientId;
    private Long propertyId;
    private Long meterId;
    private Boolean active;
}