package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.MeterProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterPropertyRepository extends JpaRepository<MeterProperty, Long> {
    boolean existsByPropertyIdAndActiveTrue(Long propertyId);
}