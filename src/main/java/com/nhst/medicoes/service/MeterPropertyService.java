package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.*;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.domain.enums.MeasurementSource;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeasurementRepository;
import com.nhst.medicoes.repository.MeterPropertyRepository;
import com.nhst.medicoes.repository.ReaderRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MeterPropertyService {
    private final MeterService meterService;
    private final PropertyService propertyService;
    private final MeterPropertyRepository meterPropertyRepository;

    private final MeasurementRepository measurementRepository;
    private final InvoiceRepository invoiceRepository;
    private final ReaderRepository readerRepository;

    @Value("${billing.price-per-m3}")
    private BigDecimal pricePerM3;
    @Value("${billing.minimum-consumption-m3}")
    private BigDecimal minimumConsumptionM3;

    @Transactional
    public void associateMeterToProperty(Long meterId, Long propertyId, Long readerId) throws Exception {
        Meter meter = meterService.findById(meterId);
        Property property = propertyService.findById(propertyId);
        Reader reader = readerRepository.findById(readerId).orElseThrow(() -> new IllegalStateException("Reader não foi encontrado"));


        MeterProperty meterProperty = new MeterProperty();
        meterProperty.setActive(true);
        meterProperty.setProperty(property);
        meterProperty.setMeter(meter);
        meterProperty.setAssignedAt(LocalDateTime.now());
        meterPropertyRepository.save(meterProperty);

        //criação do primeiro invoice, para servir de base para calcular os próximos
        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.CLOSED);
        invoice.setClosedAt(LocalDateTime.now());
        invoice.setMeter(meter);
        invoice.setPricePerM3(BigDecimal.ZERO);
        invoice.setTotalConsumedVolume(meter.getActualVolume());

        invoiceRepository.saveAndFlush(invoice);

        Measurement measurement = new Measurement();
        measurement.setInvoice(invoice);
        measurement.setConsumedVolume(meter.getActualVolume());
        measurement.setCreatedAt(LocalDateTime.now());
        measurement.setInvoice(invoice);
        measurement.setMeter(meter);
        measurement.setReader(reader);
        measurement.setSource(MeasurementSource.MANUAL_APP);
        measurement.setMeasuredAt(LocalDateTime.now());

        //measuremente zero
        measurementRepository.save(measurement);
        //invoice zero
        invoiceRepository.save(invoice);
    }

    @Transactional
    public void unlinkMeterFromProperty(Long meterId, Long propertyId) {
        MeterProperty meterProperty = meterPropertyRepository.findByPropertyIdAndActiveTrue(propertyId);
        Invoice invoice = invoiceRepository.findFirstByMeterIdAndStatusOrderByCreatedAtDesc(meterId, InvoiceStatus.OPEN).get();
        invoice.setStatus(InvoiceStatus.CLOSED);
        invoice.setClosedAt(LocalDateTime.now());
        meterProperty.setUnassignedAt(LocalDateTime.now());

        meterProperty.deactivate();
        //ao fazer unlink de meter, é necessário ter feito a leitura prévia antes
        //ao associar o novo meter, é necessário fazer a leitura zero

    }
}