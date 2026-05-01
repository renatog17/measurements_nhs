package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.Meter;
import com.nhst.medicoes.domain.MeterProperty;
import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.repository.MeterPropertyRepository;
import com.nhst.medicoes.repository.MeterRepository;
import com.nhst.medicoes.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeterService {

    private final MeterRepository meterRepository;
    private final PropertyRepository propertyRepository;
    private final MeterPropertyRepository meterPropertyRepository;
    private final InvoiceService invoiceService;

    public Meter create(String serialNumber, BigDecimal maxValue) {
        if (meterRepository.findBySerialNumber(serialNumber).isPresent()) {
            throw new IllegalStateException("Meter already exists");
        }
        return meterRepository.save(
                Meter.builder()
                        .serialNumber(serialNumber)
                        .maxValue(maxValue)
                        .value(BigDecimal.ZERO)
                        .build()
        );
    }

    public Meter findById(Long id){
        return meterRepository.findById(id).orElseThrow();
    }


    public void assignToProperty(Long meterId, Long propertyId) {
        //1 fazer associação
        if (meterPropertyRepository.existsByPropertyIdAndActiveTrue(propertyId)) {
            throw new IllegalStateException("Property already has an active meter");
        }

        Meter meter = meterRepository.findById(meterId)
                .orElseThrow(() -> new IllegalStateException("Meter not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalStateException("Property not found"));

        MeterProperty relation = MeterProperty.builder()
                .meter(meter)
                .property(property)
                .build();

        meterPropertyRepository.save(relation);

        //criar o primeiro invoice da associação
        invoiceService.createInvoice(meter);
    }
}