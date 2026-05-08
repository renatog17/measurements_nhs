package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.Meter;
import com.nhst.medicoes.repository.MeterRepository;
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

    public Meter create(String serialNumber, BigDecimal maxVolume, BigDecimal actualVolume) {
        if (meterRepository.findBySerialNumber(serialNumber).isPresent()) {
            throw new IllegalStateException("Meter already exists");
        }
        return meterRepository.save(
                Meter.builder()
                        .serialNumber(serialNumber)
                        .maxVolume(maxVolume)
                        .actualVolume(actualVolume)
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
}