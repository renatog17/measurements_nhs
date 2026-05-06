package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.ClientProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientPropertyRepository extends JpaRepository<ClientProperty, Long> {

    boolean existsByPropertyIdAndActiveTrue(Long propertyId);
    ClientProperty findByPropertyIdAndActiveTrue(Long propertyId);
    Optional<ClientProperty> findFirstByPropertyIdAndActiveTrueOrderByAssignedAtDesc(Long propertyId);
}