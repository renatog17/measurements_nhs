package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Meter;
import com.nhst.medicoes.domain.MeterProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeterPropertyRepository extends JpaRepository<MeterProperty, Long> {
    boolean existsByPropertyIdAndActiveTrue(Long propertyId);
    MeterProperty findByPropertyIdAndActiveTrue(Long propertyId);

    MeterProperty findByMeterId(Long meterId);
    Optional<MeterProperty> findFirstByMeterIdAndActiveTrueOrderByAssignedAtDesc(Long meterId);
}