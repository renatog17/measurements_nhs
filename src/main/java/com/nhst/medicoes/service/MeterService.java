package com.nhst.medicoes.service;

import com.nhst.medicoes.controller.dto.meter.MeterFilter;
import com.nhst.medicoes.controller.dto.meter.MeterResponse;
import com.nhst.medicoes.domain.Meter;
import com.nhst.medicoes.repository.MeterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class MeterService {

    private final MeterRepository meterRepository;

    public Meter create(String serialNumber, BigDecimal maxVolume) {
        if (meterRepository.findBySerialNumber(serialNumber).isPresent()) {
            throw new IllegalStateException("Meter already exists");
        }
        return meterRepository.save(
                Meter.builder()
                        .serialNumber(serialNumber)
                        .maxVolume(maxVolume)
                        .build()
        );
    }

    public Meter findById(Long id){
        return meterRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Meter not found"));
    }

    public Meter findBySerialNumber(String serialNumber){
        return meterRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalStateException("Meter not found"));
    }

    public Page<MeterResponse> findAll(
            MeterFilter filter,
            Pageable pageable
    ) {

        Page<Meter> meters = meterRepository.findAll(
                MeterSpecification.withFilters(filter),
                pageable
        );

        return meters.map(MeterResponse::fromEntity);
    }
}