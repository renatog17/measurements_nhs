package com.nhst.medicoes.service;


import com.nhst.medicoes.domain.*;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.domain.enums.MeasurementSource;
import com.nhst.medicoes.repository.InstallationRepository;
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
    private final InstallationRepository installationRepository;

    @Value("${billing.price-per-m3}")
    private BigDecimal pricePerM3;
    @Value("${billing.minimum-consumption-m3}")
    private BigDecimal minimumConsumptionM3;


    @Transactional
    public void createMeasurement(LocalDateTime measuredAt, String serialNumber, BigDecimal value, Long readerId) throws Exception {
        Installation installation = installationRepository.findByMeter_SerialNumberAndActiveTrue(serialNumber)
                .orElseThrow(() -> new IllegalStateException("Não foi encontrada instalação ativa para este medidor."));
        Reader reader = readerService.findById(readerId);

        Measurement measurement = new Measurement();
        measurement.setMeasuredAt(measuredAt);
        measurement.setConsumedVolume(value);
        measurement.setReader(reader);
        measurement.setSource(MeasurementSource.MANUAL_APP);

        Invoice invoice = invoiceRepository
                .findByInstallationAndStatusOpen(installation)
                .map(existingInvoice -> {
                    existingInvoice.setTotalConsumedVolume(value);
                    existingInvoice.setReferenceMonth(LocalDate.now());
                    return existingInvoice;
                })
                .orElseGet(() -> Invoice.builder()
                        .status(InvoiceStatus.OPEN)
                        .createdAt(LocalDateTime.now())
                        .totalConsumedVolume(value)
                        .pricePerM3(pricePerM3)
                        .referenceMonth(LocalDate.now())
                        .installation(installation)
                        .build()
                );

        measurement.setInvoice(invoice);
        invoice.getMeasurements().add(measurement);

        invoiceRepository.saveAndFlush(invoice);
        measurementRepository.saveAndFlush(measurement);


        Invoice invoiceLastClosed = invoiceRepository.findFirstByInstallationAndStatusClosedOrderByClosedAtDesc(installation.getId())
                .orElseThrow(() -> new IllegalStateException("Não foi feita a leitura inicial para esta instalação"));

        BigDecimal consumoAtual = invoice.getTotalConsumedVolume();
        BigDecimal consumoAnterior = invoiceLastClosed.getTotalConsumedVolume();

        BigDecimal diferenca = consumoAtual.subtract(consumoAnterior);

        if (diferenca.compareTo(minimumConsumptionM3) > 0
                || invoice.getMeasurements().size() >= 3) {

            invoice.setStatus(InvoiceStatus.CLOSED);
            invoice.setClosedAt(LocalDateTime.now());

            BigDecimal diff = consumoAtual.subtract(consumoAnterior);

            invoice.setTotalAmountDue(pricePerM3.multiply(diff));
            invoice.setVolumeDifference(diff);
        }

        invoiceRepository.saveAndFlush(invoice);
    }
}