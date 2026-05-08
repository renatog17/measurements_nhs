package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Installation;
import com.nhst.medicoes.domain.Meter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstallationRepository extends JpaRepository<Installation, Long> {

    boolean existsByPropertyIdAndMeterIdAndClientIdAndActiveTrue(Long propertyId, Long meterId, Long clientId);
    Optional<Installation> findFirstByPropertyIdAndActiveTrueOrderByAssignedAtDesc(Long propertyId);

    Optional<Installation> findByMeterAndActiveTrue(Meter meter);
}