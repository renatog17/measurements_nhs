//package com.nhst.medicoes.service;
//
//import com.nhst.medicoes.domain.Invoice;
//import com.nhst.medicoes.domain.Measurement;
//import com.nhst.medicoes.domain.dto.MeasurementInputDTO;
//import com.nhst.medicoes.domain.enums.InvoiceStatus;
//import com.nhst.medicoes.repository.InvoiceRepository;
//import com.nhst.medicoes.repository.MeasurementRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class MeasurementService {
//
//    private final MeasurementRepository measurementRepository;
//    private final InvoiceRepository invoiceRepository;
//
//    @Transactional
//    public void processBatch(List<MeasurementInputDTO> inputs) {
//
//        for (MeasurementInputDTO input : inputs) {
//
//            // 1. Busca fatura aberta
//            Invoice invoice = invoiceRepository
//                    .findFirstByMeterPropertyIdAndStatusOrderByCreatedAtDesc(
//                            input.meterPropertyId(),
//                            InvoiceStatus.OPEN
//                    )
//                    .orElseGet(() -> createNewInvoice(input.meterPropertyId()));
//
//            // 2. Cria measurement
//            Measurement m = new Measurement();
//            m.setMeterPropertyId(input.meterPropertyId());
//            m.setValue(input.value());
//            m.setMeasuredAt(input.measuredAt());
//            m.setInvoice(invoice);
//
//            measurementRepository.save(m);
//
//            // 3. Regra: soma valor (1 m³ = 1 real)
//            invoice.setTotalAmount(
//                    invoice.getTotalAmount().add(input.value())
//            );
//
//            // 4. Regra: fecha se >= 15
//            if (invoice.getTotalAmount().compareTo(BigDecimal.valueOf(15)) >= 0) {
//                invoice.setStatus(InvoiceStatus.CLOSED);
//                invoice.setClosedAt(LocalDateTime.now());
//            }
//
//            invoiceRepository.save(invoice);
//        }
//    }
//
//    private Invoice createNewInvoice(Long meterPropertyId) {
//        Invoice invoice = new Invoice();
//        invoice.setMeterPropertyId(meterPropertyId);
//        return invoiceRepository.save(invoice);
//    }
//}