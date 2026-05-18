package com.nhst.medicoes.service;

import com.nhst.medicoes.clock.AppTime;
import com.nhst.medicoes.domain.*;
import com.nhst.medicoes.controller.dto.installation.CreateInstallation;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.domain.enums.MeasurementSource;
import com.nhst.medicoes.repository.InstallationRepository;
import com.nhst.medicoes.repository.InvoiceRepository;
import com.nhst.medicoes.repository.MeasurementRepository;
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

    private final AppTime appTime;
    private final ClientService clientService;
    private final PropertyService propertyService;
    private final MeterService meterService;
    private final InstallationRepository installationRepository;
    private final InvoiceRepository invoiceRepository;
    private final ReaderService readerService;
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
            optionalInvoice.ifPresent(invoice -> invoice.closeInvoice(appTime.nowDateTime()));
        }
    }

    //ao fazer uma nova instalação, é necessário fazer a leitura inicial após
    @Transactional
    public Installation createInstallation(CreateInstallation req) {
        if(installationRepository.existsByMeterIdAndActiveTrue(req.meterId())){
            throw new IllegalStateException("Esse medidor já está em uma outra instalação");
        }
        if(installationRepository.existsByPropertyIdAndActiveTrue(req.propertyId())){
            throw new IllegalStateException("Essa propriedade já está em uma outra instalação");
        }
        Installation installation = new Installation();
        Client client = clientService.findById(req.clientId());
        Property property = propertyService.findById(req.propertyId());
        installation.setProperty(property);
        installation.setClient(client);
        installation.setAssignedAt(appTime.nowDateTime());

        installationRepository.saveAndFlush(installation);

        if(req.meterId() !=null){
            Meter meter = meterService.findById(req.meterId());
            installation.setMeter(meter);
            Reader reader = readerService.findById(req.readerId());

            Invoice invoice = new Invoice();
                invoice.setInstallation(installation);;
                invoice.setCreatedAt(LocalDateTime.now());
                invoice.setStatus(InvoiceStatus.CLOSED);
                invoice.setReferenceMonth(appTime.nowDate());
                invoice.setClosedAt(appTime.nowDateTime());
                invoice.setPricePerM3(BigDecimal.ZERO);
                invoice.setTotalConsumedVolume(req.volumeAtInstallation());
            invoiceRepository.saveAndFlush(invoice);

            Measurement measurement = new Measurement();
                measurement.setInvoice(invoice);
                measurement.setReader(reader);
                measurement.setSource(MeasurementSource.INITIALIZATION);
                measurement.setMeasuredAt(appTime.nowDateTime());
                measurement.setCreatedAt(appTime.nowDateTime());
                measurement.setConsumedVolume(req.volumeAtInstallation());

            measurementRepository.save(measurement);
        }
        return installation;
    }
}