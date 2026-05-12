package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.domain.enums.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PropertyRepository extends
        JpaRepository<Property, Long>,
        JpaSpecificationExecutor<Property> {

    Optional<Property> findByIdentifierCode(String identifierCode);
    long countByType(PropertyType type);
}