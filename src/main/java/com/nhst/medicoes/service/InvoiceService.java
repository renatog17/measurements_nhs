package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.Meter;
import com.nhst.medicoes.domain.MeterProperty;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeterPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Transactional
    public Invoice createInvoice(Meter meter){
        Invoice invoice = new Invoice();
        invoice.setMeter(meter);
        invoice.setStatus(InvoiceStatus.OPEN);
        return invoiceRepository.save(invoice);
    }

    public Invoice findByMeterId(Long meterId) {
        return invoiceRepository.findFirstByMeterIdAndStatusOrderByCreatedAtDesc(meterId, InvoiceStatus.OPEN).orElseThrow();
    }
}
