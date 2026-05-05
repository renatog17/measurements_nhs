package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.Measurement;
import com.nhst.medicoes.domain.Meter;
import com.nhst.medicoes.domain.MeterProperty;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.domain.enums.MeasurementSource;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeasurementRepository;
import com.nhst.medicoes.repository.MeterPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final MeasurementRepository measurementRepository;

    @Transactional
    public Invoice createInvoice(Meter meter){
        Invoice invoice = new Invoice();
        invoice.setMeter(meter);
        invoice.setStatus(InvoiceStatus.OPEN);
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> findByMeterId(Long meterId) {
        return invoiceRepository.findFirstByMeterIdAndStatusOrderByCreatedAtDesc(meterId, InvoiceStatus.OPEN);
    }

    public Invoice findLastClosedByMeterId(Long meterId) {
        return invoiceRepository.findFirstByMeterIdAndStatusOrderByCreatedAtDesc(meterId, InvoiceStatus.CLOSED).get();
    }

    public void save(Invoice invoice){
        invoiceRepository.save(invoice);
    }

}
