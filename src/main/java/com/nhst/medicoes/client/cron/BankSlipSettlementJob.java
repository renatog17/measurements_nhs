package com.nhst.medicoes.client.cron;

import com.nhst.medicoes.client.BankSlipSettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankSlipSettlementJob {

    private final BankSlipSettlementService service;

    //@Scheduled(cron = "0 */10 * * * *")
//    @Scheduled(fixedRate = 10000)
//    public void execute() {
//        service.syncPayments();
//    }
}