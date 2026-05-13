package com.nhst.medicoes.client;

import com.nhst.medicoes.client.dto.ExternalBoletoRequest;
import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.Property;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Component
public class BoletoMapper {

    public ExternalBoletoRequest toExternal(Invoice invoice, Client client, Property property) {

        BigDecimal amount = invoice.getTotalAmountDue();
        LocalDate issueDate = LocalDate.now();

        YearMonth referenceMonth = YearMonth.from(invoice.getReferenceMonth());
        LocalDate dueDate = referenceMonth.plusMonths(1).atDay(10);

        return new ExternalBoletoRequest(
                beneficiary(),
                payer(client),
                title(invoice, amount, issueDate, dueDate),
                charges()
        );
    }

    private ExternalBoletoRequest.Beneficiary beneficiary() {
        return new ExternalBoletoRequest.Beneficiary(
                "Empresa XYZ Ltda",
                "12345678000199",
                "1234",
                "56789-0"
        );
    }

    private ExternalBoletoRequest.Payer payer(Client client) {

        var address = client.getAddress();

        return new ExternalBoletoRequest.Payer(
                client.getName(),
                client.getDocument(),
                new ExternalBoletoRequest.Address(
                        address != null ? address.getStreet() : null,
                        address != null ? address.getNumber() : null,
                        address != null ? address.getCity() : null,
                        address != null ? address.getState() : null,
                        address != null ? address.getZipCode() : null
                )
        );
    }

    private ExternalBoletoRequest.Title title(
            Invoice invoice,
            BigDecimal amount,
            LocalDate issueDate,
            LocalDate dueDate
    ) {
        return new ExternalBoletoRequest.Title(
                "INV-" + invoice.getId(),
                amount,
                dueDate,
                issueDate
        );
    }

    private ExternalBoletoRequest.Charges charges() {
        return new ExternalBoletoRequest.Charges(
                new BigDecimal("2.0"),
                new BigDecimal("0.033")
        );
    }
}