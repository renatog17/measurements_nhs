package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.ClientProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientPropertyRepository extends JpaRepository<ClientProperty, Long> {

    boolean existsByPropertyIdAndActiveTrue(Long propertyId);
}