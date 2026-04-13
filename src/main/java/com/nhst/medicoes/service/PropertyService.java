package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public Property create(String address, String city, String identifierCode) {

        if (propertyRepository.findByIdentifierCode(identifierCode).isPresent()) {
            throw new IllegalStateException("Property already exists with this identifier code");
        }

        Property property = Property.builder()
                .address(address)
                .city(city)
                .identifierCode(identifierCode)
                .build();

        return propertyRepository.save(property);
    }
}