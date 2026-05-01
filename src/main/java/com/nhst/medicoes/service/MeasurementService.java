package com.nhst.medicoes.service;


import com.nhst.medicoes.domain.*;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.domain.enums.MeasurementSource;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final MeterService meterService;
    private final ReaderService readerService;
    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;

    @Transactional
    public void createMeasurement(LocalDateTime measuredAt, Long meterId, BigDecimal value, Long readerId) {
        Meter meter = meterService.findById(meterId);
        Invoice invoice = invoiceService.findByMeterId(meterId);

        Reader reader = readerService.findById(readerId);

        Measurement measurement = new Measurement();

        measurement.setMeasuredAt(measuredAt);
        measurement.setMeter(meter);
        measurement.setValue(value);
        measurement.setReader(reader);
        measurement.setSource(MeasurementSource.MANUAL_APP);

        //to do: fazer validação e checagem se o valor corresponde ao consumo do cliente
        meter.setValue(measurement.getValue());

        if (
        invoice.getTotalValue().compareTo(new BigDecimal("20.0")) > 0
        ||
        invoice.getMeasurements().size() > 3
        ) {
            invoice.setStatus(InvoiceStatus.CLOSED);
            invoice.setClosedAt(LocalDateTime.now());
            invoiceRepository.saveAndFlush(invoice);
            Invoice newInvoice = invoiceService.createInvoice(meter);
            newInvoice.setTotalValue(newInvoice.getTotalValue().add(measurement.getValue()));
            measurement.setInvoice(newInvoice);
        }else{
            invoice.setTotalValue(invoice.getTotalValue().add(measurement.getValue()));
            measurement.setInvoice(invoice);
        }

        measurementRepository.save(measurement);
    }
}