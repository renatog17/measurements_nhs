package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.Meter;
import com.nhst.medicoes.domain.MeterProperty;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeterPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final MeterPropertyRepository meterPropertyRepository;

    public void createInvoice(MeterProperty meterProperty){
        Invoice invoice = new Invoice();
        invoice.setMeterProperty(meterProperty);

        invoiceRepository.save(invoice);
    }

    public Invoice findByMeterId(Long meterId) {

        MeterProperty mp = meterPropertyRepository.findByMeterId(meterId);

        return invoiceRepository.findFirstByMeterPropertyIdAndStatusOrderByCreatedAtDesc(mp.getId(), InvoiceStatus.OPEN).orElseThrow();
    }
}
