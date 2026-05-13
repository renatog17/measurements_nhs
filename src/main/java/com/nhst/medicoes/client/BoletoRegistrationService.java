package com.nhst.medicoes.client;

import com.nhst.medicoes.client.dto.ExternalBoletoRequest;
import com.nhst.medicoes.client.dto.ExternalBoletoResponse;
import com.nhst.medicoes.comunication.templates.InvoiceAvailableEmail;
import com.nhst.medicoes.domain.BankSlip;
import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.domain.Installation;
import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.domain.enums.BankSlipStatus;
import com.nhst.medicoes.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoletoRegistrationService {

    private final InvoiceRepository invoiceRepository;
    private final BankSlipRepository bankSlipRepository;
    private final BankApiClient bankApiClient;
    private final BoletoMapper mapper;
    private final InvoiceAvailableEmail InvoiceAvailableEmail;

    @Transactional
    public void registerBoletos() {
        List<Invoice> invoices = invoiceRepository.findInvoicesWithoutBankSlipAndStatusClosed();
        for (Invoice invoice : invoices) {
            if (invoice.getPricePerM3().compareTo(BigDecimal.ZERO) == 0) continue;

            Installation installation = invoice.getInstallation();
            Client client = installation.getClient();
            Property property = installation.getProperty();

            ExternalBoletoRequest request = mapper.toExternal(invoice, client, property);
            ExternalBoletoResponse response = bankApiClient.createBoleto(request);

            if (response == null || response.errorCode() != null) {
                continue;
            }

            BankSlip bankSlip = BankSlip.builder()
                    .nossoNumero(response.nossoNumero())
                    .digitableLine(response.digitableLine())
                    .barcode(response.barcode())
                    .amount(response.amount())
                    .dueDate(response.dueDate())
                    .status(BankSlipStatus.REGISTERED)
                    .active(true)
                    .paid(false)
                    .invoice(invoice)
                    .build();

            bankSlipRepository.save(bankSlip);
            InvoiceAvailableEmail.enviarEmail(invoice);
        }
    }
}