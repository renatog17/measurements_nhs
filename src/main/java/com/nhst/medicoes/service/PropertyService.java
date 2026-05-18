package com.nhst.medicoes.service;

import com.nhst.medicoes.clock.AppTime;
import com.nhst.medicoes.controller.dto.address.CreateAddressRequest;
import com.nhst.medicoes.controller.dto.property.PropertyFilter;
import com.nhst.medicoes.controller.dto.property.PropertyResponse;
import com.nhst.medicoes.domain.Address;
import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.domain.enums.PropertyType;
import com.nhst.medicoes.repository.AddressRepository;
import com.nhst.medicoes.repository.PropertyRepository;
import com.nhst.medicoes.service.specification.PropertySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final AppTime appTime;
    private final PropertyRepository propertyRepository;
    private final AddressRepository addressRepository;

    public Property create(
            CreateAddressRequest addressRequest,
            Long parentPropertyId,
            String name,
            PropertyType propertyType
    ) {

        Address address = Address.builder()
                .street(addressRequest.street())
                .number(addressRequest.number())
                .complement(addressRequest.complement())
                .neighborhood(addressRequest.neighborhood())
                .city(addressRequest.city())
                .state(addressRequest.state())
                .zipCode(addressRequest.zipCode())
                .build();

        addressRepository.saveAndFlush(address);

        long countByType = propertyRepository.countByType(propertyType);

        String generatedCode =
                propertyType.name().substring(0, 4)
                        + String.format("%04d", countByType + 1);

        Property property = Property.builder()
                .address(address)
                .identifierCode(generatedCode)
                .name(name)
                .type(propertyType)
                .createdAt(appTime.nowDateTime())
                .build();

        if (parentPropertyId != null) {
            Property parentProperty = propertyRepository.findById(parentPropertyId)
                    .orElseThrow(() -> new IllegalStateException("Property not found"));

            property.setParentProperty(parentProperty);
        }

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