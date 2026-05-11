package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MeterRepository extends JpaRepository<Meter, Long>
        , JpaSpecificationExecutor<Meter> {
    Optional<Meter> findBySerialNumber(String serialNumber);
}