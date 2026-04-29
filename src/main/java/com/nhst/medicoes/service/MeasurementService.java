package com.nhst.medicoes.service;


import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.Measurement;
import com.nhst.medicoes.domain.Meter;
import com.nhst.medicoes.domain.MeterProperty;
import com.nhst.medicoes.domain.enums.MeasurementSource;
import com.nhst.medicoes.repository.MeasurementRepository;
import com.nhst.medicoes.repository.MeterPropertyRepository;
import com.nhst.medicoes.repository.MeterRepository;
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
    private final MeterPropertyRepository meterPropertyRepository;

    public void createMeasurement(LocalDateTime measuredAt, Long meterId, BigDecimal value, Long readerId) {
        Measurement measurement = new Measurement();
        measurement.setMeasuredAt(measuredAt);
        Meter meter = meterService.findById(meterId);
        measurement.setMeter(meter);
        measurement.setValue(value);
        measurement.setReader(readerService.findById(readerId));

        Invoice invoice = invoiceService.findByMeterId(meterId);

        measurement.setInvoice(invoice);

        MeterProperty mp = meterPropertyRepository.findByMeterId(meter.getId());
        measurement.setMeterProperty(mp);
        measurement.setSource(MeasurementSource.MANUAL_APP);
        measurementRepository.save(measurement);
    }
}