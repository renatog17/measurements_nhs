package com.nhst.medicoes.client.cron;

import com.nhst.medicoes.client.BoletoRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoletoRegistrationJob {

    private final BoletoRegistrationService service;

    //@Scheduled(cron = "0 0 2 * * *")
//    @Scheduled(fixedRate = 10000)
//    public void execute() {
//        service.registerBoletos();
//    }
}