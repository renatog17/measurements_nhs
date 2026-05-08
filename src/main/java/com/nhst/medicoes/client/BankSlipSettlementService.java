package com.nhst.medicoes.client;

import com.nhst.medicoes.client.dto.BoletoStatusResponse;
import com.nhst.medicoes.domain.BankSlip;
import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.enums.BankSlipStatus;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.repository.BankSlipRepository;
import com.nhst.medicoes.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankSlipSettlementService {

    private final BankSlipRepository bankSlipRepository;
    private final InvoiceRepository invoiceRepository;
    private final BankApiClient bankApiClient;

    @Transactional
    public void syncPayments() {

        List<BankSlip> bankSlips =
                bankSlipRepository
                        .findByStatusAndPaidFalse(
                                BankSlipStatus.REGISTERED
                        );

        for (BankSlip bankSlip : bankSlips) {

            BoletoStatusResponse response =
                    bankApiClient.getStatus(
                            bankSlip.getNossoNumero()
                    );

            if (response == null) {
                continue;
            }

            if (response.status() == BankSlipStatus.PAID) {

                bankSlip.setPaid(true);
                bankSlip.setPaidAt(response.paidAt());
                bankSlip.setStatus(BankSlipStatus.PAID);

                bankSlipRepository.save(bankSlip);

                Invoice invoice = bankSlip.getInvoice();

                if (invoice != null) {

                    invoice.setStatus(InvoiceStatus.PAID);

                    invoiceRepository.save(invoice);
                }
            }
        }
    }
}