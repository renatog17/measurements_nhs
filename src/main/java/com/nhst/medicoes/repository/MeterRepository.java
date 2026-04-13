package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Meter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeterRepository extends JpaRepository<Meter, Long> {
    Optional<Meter> findBySerialNumber(String serialNumber);
}