package com.nhst.medicoes.client;

import com.nhst.medicoes.client.dto.ExternalBoletoRequest;
import com.nhst.medicoes.client.dto.ExternalBoletoResponse;
import com.nhst.medicoes.domain.BankSlip;
import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.domain.ClientProperty;
import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.MeterProperty;
import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.domain.enums.BankSlipStatus;
import com.nhst.medicoes.repository.BankSlipRepository;
import com.nhst.medicoes.repository.ClientPropertyRepository;
import com.nhst.medicoes.repository.ClientRepository;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeterPropertyRepository;
import com.nhst.medicoes.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoletoRegistrationService {

    private final InvoiceRepository invoiceRepository;
    private final BankSlipRepository bankSlipRepository;
    private final MeterPropertyRepository meterPropertyRepository;
    private final PropertyRepository propertyRepository;
    private final ClientPropertyRepository clientPropertyRepository;
    private final ClientRepository clientRepository;
    private final BankApiClient bankApiClient;
    private final BoletoMapper mapper;

    @Transactional
    public void registerBoletos() {
        List<Invoice> invoices = invoiceRepository.findInvoicesWithoutBankSlip();

        for (Invoice invoice : invoices) {

            if (bankSlipRepository.existsByInvoiceId(invoice.getId())) {
                continue;
            }

            MeterProperty meterProperty = meterPropertyRepository
                    .findFirstByMeterIdAndActiveTrueOrderByAssignedAtDesc(invoice.getMeter().getId())
                    .orElse(null);

            if (meterProperty == null || meterProperty.getProperty() == null) {
                continue;
            }

            Property property = propertyRepository
                    .findById(meterProperty.getProperty().getId())
                    .orElse(null);

            if (property == null) {
                continue;
            }

            ClientProperty clientProperty = clientPropertyRepository
                    .findFirstByPropertyIdAndActiveTrueOrderByAssignedAtDesc(property.getId())
                    .orElse(null);

            if (clientProperty == null || clientProperty.getClient() == null) {
                continue;
            }

            Client client = clientRepository
                    .findById(clientProperty.getClient().getId())
                    .orElse(null);

            if (client == null) {
                continue;
            }

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
                    .invoice(invoice)
                    .build();

            bankSlipRepository.save(bankSlip);
        }
    }
}