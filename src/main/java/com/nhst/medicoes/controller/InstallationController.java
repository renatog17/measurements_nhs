package com.nhst.medicoes.controller;

import com.nhst.medicoes.controller.dto.installation.CreateInstallation;
import com.nhst.medicoes.controller.dto.installation.InstallationResponse;
import com.nhst.medicoes.domain.Installation;
import com.nhst.medicoes.service.InstallationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/installation")
public class InstallationController {

    private final InstallationService installationService;

    @PostMapping("")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<InstallationResponse> assign(@Valid @RequestBody CreateInstallation req) {

        Installation installation = installationService.createInstallation(req);
        InstallationResponse response = InstallationResponse.fromEntity(installation);
        return ResponseEntity
                .created(URI.create("/installation/" + response.getId()))
                .body(response);
    }

    @PostMapping("/disable/{serialNumber}")
    @PreAuthorize("hasRole('OPERATOR')")
    public void unlink(@PathVariable String serialNumber){
        installationService.disableInstallation(serialNumber);
    }
}
