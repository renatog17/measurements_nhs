package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.dto.AssignMeterRequest;
import com.nhst.medicoes.domain.dto.AssignPropertyRequest;
import com.nhst.medicoes.service.ClientPropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clientproperty")
public class ClientPropertyController {

    private final ClientPropertyService clientPropertyService;

    @PostMapping("/associate")
    @PreAuthorize("hasRole('OPERATOR')")
    public void assign(@RequestBody AssignPropertyRequest req) {
        clientPropertyService.associatePropertyToClient(req.clientId(), req.propertyId());
    }

    @PostMapping("/unlink")
    @PreAuthorize("hasRole('OPERATOR')")
    public void unlink(@RequestBody @Valid AssignPropertyRequest req){
        clientPropertyService.unlinkClientFromProperty(req.propertyId());
    }
}
