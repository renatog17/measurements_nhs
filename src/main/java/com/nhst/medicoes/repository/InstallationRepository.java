package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Installation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstallationRepository extends JpaRepository<Installation, Long> {

    boolean existsByPropertyIdAndActiveTrue(Long propertyId);
    Optional<Installation> findFirstByPropertyIdAndActiveTrueOrderByAssignedAtDesc(Long propertyId);
}