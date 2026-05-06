package com.nhst.medicoes.cron;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceJob {

    private final BoletoRegistrationJob service;

   // @Scheduled(cron = "0 0 22 * * *")
   @Scheduled(fixedRate = 10000)
    public void run() {
        service.process();
    }
}