package com.nhst.medicoes.cron;

import com.nhst.medicoes.boletogateway.BoletoGateway;
import com.nhst.medicoes.boletogateway.MockBoletoGateway;
import com.nhst.medicoes.domain.*;
import com.nhst.medicoes.domain.bank.BankBoletoRegisterRequest;
import com.nhst.medicoes.domain.bank.BankBoletoRegisterResponse;
import com.nhst.medicoes.domain.enums.BankSlipStatus;
import com.nhst.medicoes.repository.BankSlipRepository;
import com.nhst.medicoes.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoletoRegistrationJob {

    private final InvoiceRepository invoiceRepository;
    private final MockBoletoGateway boletoGateway;
    private final BankSlipRepository bankSlipRepository;

    void process() {
        List<Invoice> invoices = invoiceRepository.findClosedWithoutActiveBankSlip();

        for (Invoice invoice : invoices) {

            // 1. Encontrar Property ativa
            Property property = invoice.getMeter()
                    .getMeterProperties()
                    .stream()
                    .filter(MeterProperty::isActive)
                    .findFirst()
                    .orElseThrow()
                    .getProperty();

            // 2. Encontrar Client ativo
            Client client = property.getClientProperties()
                    .stream()
                    .filter(cp -> cp.isActive())
                    .findFirst()
                    .orElseThrow()
                    .getClient();

            // 3. Calcular valor da invoice
            BigDecimal amount = invoice.getTotalConsumedVolume()
                    .multiply(invoice.getPricePerM3());

            // 4. Definir vencimento (ex: +10 dias após fechamento)
            LocalDate dueDate = invoice.getClosedAt()
                    .toLocalDate()
                    .plusDays(10);

            // 5. Montar request
            BankBoletoRegisterRequest req = new BankBoletoRegisterRequest(

                    // Beneficiário (fixo por enquanto)
                    new BankBoletoRegisterRequest.Beneficiary(
                            "Minha Empresa",
                            "12345678000100",
                            "1234",
                            "56789-0"
                    ),

                    // Pagador
                    new BankBoletoRegisterRequest.Payer(
                            client.getName(),
                            client.getCpf(),
                            new BankBoletoRegisterRequest.Address(
                                    property.getAddress(),
                                    "S/N",
                                    property.getCity(),
                                    "BA",
                                    "00000000"
                            )
                    ),

                    // Título
                    new BankBoletoRegisterRequest.Title(
                            "INV-" + invoice.getId(),
                            amount,
                            dueDate,
                            LocalDate.now()
                    ),

                    // Instruções
                    new BankBoletoRegisterRequest.Instructions(
                            new BigDecimal("2.0"),     // multa %
                            new BigDecimal("0.033")    // juros % ao dia
                    )
            );
            BankBoletoRegisterResponse response = boletoGateway.register(req);

            BankSlip bankSlip = new BankSlip();
            bankSlip.setActive(true);
            bankSlip.setBarcode(response.barcode());
            bankSlip.setAmount(response.amount());
            bankSlip.setPaid(false);
            bankSlip.setStatus(BankSlipStatus.REGISTERED);
            bankSlip.setNossoNumero(response.nossoNumero());
            bankSlip.setDigitableLine(response.digitableLine());

            bankSlipRepository.save(bankSlip);
        }


    }
}