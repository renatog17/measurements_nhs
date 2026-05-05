package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.*;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.domain.enums.MeasurementSource;
import com.nhst.medicoes.repository.ClientPropertyRepository;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeterPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientPropertyService {

    private final ClientPropertyRepository clientPropertyRepository;
    private final ClientService clientService;
    private final PropertyService propertyService;
    private final MeterPropertyRepository meterPropertyRepository;
    private final InvoiceRepository invoiceRepository;

    public void associatePropertyToClient(Long clientId, Long propertyId) {
        //1 fazer associação
        if (clientPropertyRepository.existsByPropertyIdAndActiveTrue(propertyId)) {
            throw new IllegalStateException("Property already has an active meter");
        }

        Client client = clientService.findById(clientId);
        Property property = propertyService.findById(propertyId);

        ClientProperty relation = ClientProperty.builder()
                .client(client)
                .property(property)
                .build();

        clientPropertyRepository.save(relation);
    }

    @Transactional
    public void unlinkClientFromProperty(Long clientId, Long propertyId) {
        ClientProperty clientProperty = clientPropertyRepository.findByPropertyIdAndActiveTrue(propertyId);
        clientProperty.deactivate();
        MeterProperty meterProperty = meterPropertyRepository.findByPropertyIdAndActiveTrue(propertyId);
        Meter meter = meterProperty.getMeter();
        Invoice invoice = invoiceRepository.findFirstByMeterIdAndStatusOrderByCreatedAtDesc(meter.getId(), InvoiceStatus.OPEN).get();
        invoice.setStatus(InvoiceStatus.CLOSED);
        invoice.setClosedAt(LocalDateTime.now());

        //ao linkar um novo client a uma property, é necessário fazer a leitura zero

    }
}
