//package com.nhst.medicoes.client;
//
//import com.nhst.medicoes.client.dto.ExternalBoletoRequest;
//import com.nhst.medicoes.domain.Client;
//import com.nhst.medicoes.domain.Invoice;
//import com.nhst.medicoes.domain.Property;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Component
//public class BoletoMapper {
//
//    public ExternalBoletoRequest toExternal(Invoice invoice, Client client, Property property) {
//
//        BigDecimal amount = invoice.getTotalAmountDue();
//
//        LocalDate issueDate = LocalDate.now();
//
//        LocalDate dueDate = invoice.getReferenceMonth().plusMonths(1);
//
//        return new ExternalBoletoRequest(
//                new ExternalBoletoRequest.Beneficiary(
//                        "Empresa XYZ Ltda",
//                        "12345678000199",
//                        "1234",
//                        "56789-0"
//                ),
//                new ExternalBoletoRequest.Payer(
//                        client.getName(),
//                        client.getCpf(),
//                        new ExternalBoletoRequest.Address(
//                                property.getAddress(),
//                                "100",
//                                property.getCity(),
//                                "BA",
//                                "40000-000"
//                        )
//                ),
//                new ExternalBoletoRequest.Title(
//                        "INV-" + invoice.getId(),
//                        amount,
//                        dueDate,
//                        issueDate
//                ),
//                new ExternalBoletoRequest.Charges(
//                        new BigDecimal("2.0"),
//                        new BigDecimal("0.033")
//                )
//        );
//    }
//}