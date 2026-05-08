package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.dto.CreateInstallation;
import com.nhst.medicoes.service.InstallationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/installation")
public class InstallationController {

    private final InstallationService installationService;

    @PostMapping("")
    @PreAuthorize("hasRole('OPERATOR')")
    public void assign(@RequestBody CreateInstallation req) {

        installationService.createInstallation(req);
    }

    @PostMapping("/disable/{serialNumber}")
    @PreAuthorize("hasRole('OPERATOR')")
    public void unlink(@PathVariable String serialNumber){
        installationService.disableInstallation(serialNumber);
    }
}
