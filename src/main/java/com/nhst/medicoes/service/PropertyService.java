package com.nhst.medicoes.service;

import com.nhst.medicoes.controller.dto.property.PropertyFilter;
import com.nhst.medicoes.controller.dto.property.PropertyResponse;
import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public Property create(String address, String city, String identifierCode) {

        if (propertyRepository.findByIdentifierCode(identifierCode).isPresent()) {
            throw new IllegalStateException("Property already exists with this identifier code");
        }
        Property property = new Property(address, city, identifierCode);

        return propertyRepository.save(property);
    }

    public Property findById(Long propertyId) {
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalStateException("Property not found"));
    }
    public Page<PropertyResponse> findAll(
            PropertyFilter filter,
            Pageable pageable
    ) {

        Page<Property> properties = propertyRepository.findAll(
                PropertySpecification.withFilters(filter),
                pageable
        );

        return properties.map(PropertyResponse::fromEntity);
    }
}