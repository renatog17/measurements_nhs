package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByIdentifierCode(String identifierCode);
}