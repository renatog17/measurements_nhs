package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.*;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.repository.ClientPropertyRepository;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeterPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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
            throw new IllegalStateException("Property already has an active owner");
        }

        Client client = clientService.findById(clientId);
        Property property = propertyService.findById(propertyId);

        Installation relation = Installation.builder()
                .client(client)
                .property(property)
                .build();

        clientPropertyRepository.save(relation);
    }

    @Transactional
    public void unlinkClientFromProperty(Long propertyId) {
        Installation clientProperty = clientPropertyRepository.findFirstByPropertyIdAndActiveTrueOrderByAssignedAtDesc(propertyId).get();
        clientProperty.deactivate();

        MeterProperty meterProperty = meterPropertyRepository.findFirstByPropertyIdAndActiveTrueOrderByAssignedAtDesc(propertyId).get();

        Optional<Invoice> optInvoice = invoiceRepository.findFirstByMeterPropertyAndStatus(meterProperty, InvoiceStatus.OPEN);

        if(optInvoice.isPresent()){
            Invoice invoice = optInvoice.get();
            invoice.setStatus(InvoiceStatus.CLOSED);
            invoice.setClosedAt(LocalDateTime.now());
        }

        //após linkar um novo client a uma property, é necessário fazer a leitura zero

    }
}
