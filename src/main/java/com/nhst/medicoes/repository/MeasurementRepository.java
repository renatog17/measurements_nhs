package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    List<Measurement> findByMeterPropertyId(Long meterPropertyId);


}