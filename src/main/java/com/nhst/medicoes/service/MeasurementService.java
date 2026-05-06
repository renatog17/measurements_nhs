package com.nhst.medicoes.service;


import com.nhst.medicoes.domain.*;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.domain.enums.MeasurementSource;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final MeterService meterService;
    private final ReaderService readerService;
    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;

    @Value("${billing.price-per-m3}")
    private BigDecimal pricePerM3;
    @Value("${billing.minimum-consumption-m3}")
    private BigDecimal minimumConsumptionM3;


    @Transactional
    public void createMeasurement(LocalDateTime measuredAt, Long meterId, BigDecimal value, Long readerId) throws Exception {
        Meter meter = meterService.findById(meterId);
        Optional<Invoice> optionalInvoice = invoiceService.findByMeterId(meterId);
        Invoice lastClosed = invoiceService.findLastClosedByMeterId(meterId);
        Reader reader = readerService.findById(readerId);

        Measurement measurement = new Measurement();
        measurement.setMeasuredAt(measuredAt);
        measurement.setMeter(meter);
        measurement.setConsumedVolume(value);
        measurement.setReader(reader);
        measurement.setSource(MeasurementSource.MANUAL_APP);
        meter.setActualVolume(value);

        Invoice invoice;

        //criar ou usar um invoice já existente
        if(optionalInvoice.isPresent()){
            invoice = optionalInvoice.get();
            measurement.setInvoice(invoice);
            invoice.setTotalConsumedVolume(meter.getActualVolume());
            invoice.setReferenceMonth(LocalDate.now());
        }else{
            invoice = new Invoice();
            invoice.setCreatedAt(LocalDateTime.now());
            invoice.setMeter(meter);
            invoice.setTotalConsumedVolume(meter.getActualVolume());
            invoice.setPricePerM3(pricePerM3);
            invoice.setReferenceMonth(LocalDate.now());
            measurement.setInvoice(invoice);
        }

        //verificar se já pode fechar o invoice
        BigDecimal consumoAtual = invoice.getTotalConsumedVolume();
        BigDecimal consumoAnterior = lastClosed.getTotalConsumedVolume();

        BigDecimal diferenca = consumoAtual.subtract(consumoAnterior);

        if (diferenca.compareTo(minimumConsumptionM3) > 0
        ||
        invoice.getMeasurements().size() > 3
        ) {
            invoice.setStatus(InvoiceStatus.CLOSED);
            invoice.setClosedAt(LocalDateTime.now());

        }
        invoiceRepository.saveAndFlush(invoice);
        measurementRepository.save(measurement);
    }
}