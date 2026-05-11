package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.*;
import com.nhst.medicoes.controller.dto.CreateInstallation;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.domain.enums.MeasurementSource;
import com.nhst.medicoes.repository.InstallationRepository;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeasurementRepository;
import com.nhst.medicoes.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstallationService {

    private final ClientService clientService;
    private final PropertyService propertyService;
    private final MeterService meterService;
    private final InstallationRepository installationRepository;
    private final InvoiceRepository invoiceRepository;
    private final ReaderRepository readerRepository;
    private final MeasurementRepository measurementRepository;

    //antes de desativar uma installação, é necessário fazer a leitura final
    @Transactional
    public void disableInstallation(String meterSerialNumber) {
        Meter meter = meterService.findBySerialNumber(meterSerialNumber);
        Optional<Installation> optionalInstallation = installationRepository.findByMeterAndActiveTrue(meter);

        if(optionalInstallation.isPresent()){
            Installation installation = optionalInstallation.get();
            installation.setActive(false);
            Optional<Invoice> optionalInvoice = invoiceRepository.findByInstallationAndStatusOpen(installation);
            optionalInvoice.ifPresent(Invoice::closeInvoice);
        }
    }

    //ao fazer uma nova instalação, é necessário fazer a leitura inicial após
    @Transactional
    public void createInstallation(CreateInstallation req) {

        if(installationRepository.existsByPropertyIdAndMeterIdAndClientIdAndActiveTrue(req.propertyId(), req.meterId(), req.clientId())){
            throw new IllegalStateException("This installation already exists.");
        }

        Client client = clientService.findById(req.clientId());
        Property property = propertyService.findById(req.propertyId());
        Meter meter = meterService.findById(req.meterId());
        Reader reader = readerRepository.findById(req.readerId()).get();

        Installation installation = new Installation();
        installation.setProperty(property);
        installation.setMeter(meter);
        installation.setClient(client);
        installation.setAssignedAt(LocalDateTime.now());
        installation.setVolumeAtAssigned(req.volumeAtAssigned());

        installationRepository.saveAndFlush(installation);

        Invoice invoice = new Invoice();
        invoice.setInstallation(installation);;
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setStatus(InvoiceStatus.CLOSED);
        invoice.setTotalConsumedVolume(req.volumeAtAssigned());
        invoice.setReferenceMonth(LocalDate.now());
        invoice.setClosedAt(LocalDateTime.now());
        invoice.setPricePerM3(BigDecimal.ZERO);

        invoiceRepository.saveAndFlush(invoice);

        Measurement measurement = new Measurement();
        measurement.setInvoice(invoice);
        measurement.setReader(reader);
        measurement.setConsumedVolume(req.volumeAtAssigned());
        measurement.setSource(MeasurementSource.INITIALIZATION);
        measurement.setMeasuredAt(LocalDateTime.now());
        measurement.setCreatedAt(LocalDateTime.now());

        measurementRepository.save(measurement);

    }
}