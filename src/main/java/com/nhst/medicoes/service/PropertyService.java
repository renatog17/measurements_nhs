package com.nhst.medicoes.service;

import com.nhst.medicoes.controller.dto.address.CreateAddressRequest;
import com.nhst.medicoes.controller.dto.property.PropertyFilter;
import com.nhst.medicoes.controller.dto.property.PropertyResponse;
import com.nhst.medicoes.domain.Address;
import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.repository.AddressRepository;
import com.nhst.medicoes.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final AddressRepository addressRepository;

    public Property create(String identifierCode,
                           CreateAddressRequest addressRequest,
                           Long parentPropertyId,
                           String name) {


        if (propertyRepository.findByIdentifierCode(identifierCode).isPresent()) {
            throw new IllegalStateException("Property already exists with this identifier code");
        }

        Address address = new Address();

        address.setStreet(addressRequest.street());
        address.setNumber(addressRequest.number());
        address.setComplement(addressRequest.complement());
        address.setNeighborhood(addressRequest.neighborhood());
        address.setCity(addressRequest.city());
        address.setState(addressRequest.state());
        address.setZipCode(addressRequest.zipCode());

        addressRepository.saveAndFlush(address);

        Property property = new Property(address, identifierCode, name);

        if(parentPropertyId != null) {
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