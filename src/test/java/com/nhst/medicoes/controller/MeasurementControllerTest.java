package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.*;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import com.nhst.medicoes.domain.enums.MeasurementSource;
import com.nhst.medicoes.domain.enums.PersonType;
import com.nhst.medicoes.domain.enums.PropertyType;
import com.nhst.medicoes.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class MeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private InstallationRepository installationRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    private Client client;
    private Property property;
    private Meter meter;
    private Reader reader;
    private Installation installation;
    private Invoice invoice;
    private Measurement measurement;

    @BeforeEach
    void setup() {

        Address address = addressRepository.saveAndFlush(
                Address.builder()
                        .street("Rua A")
                        .number("10")
                        .neighborhood("Centro")
                        .city("Camaçari")
                        .state("BA")
                        .zipCode("42800-000")
                        .build()
        );

        client = clientRepository.saveAndFlush(
                Client.builder()
                        .name("Renato")
                        .email("renato@test.com")
                        .document("12345678900")
                        .personType(PersonType.PHYSICAL)
                        .address(address)
                        .build()
        );

        property = propertyRepository.saveAndFlush(
                Property.builder()
                        .address(address)
                        .name("Casa 1")
                        .type(PropertyType.RESIDENTIAL)
                        .build()
        );

        meter = meterRepository.saveAndFlush(
                Meter.builder()
                        .serialNumber("M-001")
                        .maxVolume(new BigDecimal("99999"))
                        .reset(0)
                        .active(true)
                        .build()
        );

        reader = readerRepository.saveAndFlush(
                Reader.builder()
                        .name("João")
                        .employeeCode("EMP-001")
                        .active(true)
                        .build()
        );

        installation = installationRepository.saveAndFlush(
                Installation.builder()
                        .meter(meter)
                        .client(client)
                        .property(property)
                        .active(true)
                        .assignedAt(LocalDateTime.now())
                        .build()
        );

        invoice = invoiceRepository.saveAndFlush(
                Invoice.builder()
                        .referenceMonth(LocalDate.now())
                        .installation(installation)
                        .totalConsumedVolume(BigDecimal.valueOf(55.0))
                        .pricePerM3(BigDecimal.ZERO)
                        .status(InvoiceStatus.CLOSED)
                        .createdAt(LocalDateTime.now())
                        .closedAt(LocalDateTime.now())
                        .build()
        );

        measurement = measurementRepository.saveAndFlush(
                Measurement.builder()
                        .reader(reader)
                        .invoice(invoice)
                        .source(MeasurementSource.INITIALIZATION)
                        .consumedVolume(invoice.getTotalConsumedVolume())
                        .measuredAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .build()
        );

    }

    private String buildRequest(LocalDateTime measuredAt, String serial, double volume, Long readerId) {
        return """
            {
              "measuredAt": "%s",
              "meterSerialNumber": "%s",
              "actualVolume": %s,
              "readerId": %d
            }
            """.formatted(measuredAt, serial, volume, readerId);
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCreateOpenInvoiceAndMeasurement_whenReceivingManualMeasurement() throws Exception {

        long initialInvoiceCount = invoiceRepository.count();
        long initialMeasurementCount = measurementRepository.count();

        mockMvc.perform(post("/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRequest(
                                LocalDateTime.now().plusDays(1),
                                meter.getSerialNumber(),
                                65.0,
                                reader.getId()
                        )))
                .andExpect(status().isOk());

        long finalInvoiceCount = invoiceRepository.count();
        long finalMeasurementCount = measurementRepository.count();

        assertThat(finalMeasurementCount)
                .isEqualTo(initialMeasurementCount + 1);

        assertThat(finalInvoiceCount)
                .isEqualTo(initialInvoiceCount + 1);

        // =========================
        // VALIDAR INVOICE CRIADA
        // =========================
        Invoice createdInvoice = invoiceRepository.findAll()
                .stream()
                .filter(i -> i.getStatus() == InvoiceStatus.OPEN)
                .findFirst()
                .orElseThrow();

        assertThat(createdInvoice.getStatus())
                .isEqualTo(InvoiceStatus.OPEN);

        assertThat(createdInvoice.getTotalConsumedVolume())
                .isEqualTo(BigDecimal.valueOf(65.0));


        // =========================
        // VALIDAR MEASUREMENT CRIADA
        // =========================
        Measurement createdMeasurement = measurementRepository.findAll()
                .stream()
                .filter(m -> m.getSource() == MeasurementSource.MANUAL_APP)
                .findFirst()
                .orElseThrow();

        assertThat(createdMeasurement.getConsumedVolume())
                .isEqualTo(BigDecimal.valueOf(65.0));

        assertThat(createdMeasurement.getReader().getId())
                .isEqualTo(reader.getId());

        assertThat(createdMeasurement.getInvoice())
                .isNotNull();
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCloseInvoice_whenReceivingHighVolumeMeasurement() throws Exception {

        long initialInvoiceCount = invoiceRepository.count();
        long initialMeasurementCount = measurementRepository.count();

        mockMvc.perform(post("/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRequest(
                                LocalDateTime.now().plusDays(1),
                                meter.getSerialNumber(),
                                85.0,
                                reader.getId()
                        )))
                .andExpect(status().isOk());

        long finalInvoiceCount = invoiceRepository.count();
        long finalMeasurementCount = measurementRepository.count();

        assertThat(finalMeasurementCount)
                .isEqualTo(initialMeasurementCount + 1);

        assertThat(finalInvoiceCount)
                .isEqualTo(initialInvoiceCount + 1);

        // =========================
        // VALIDAR INVOICE CRIADA
        // =========================
        Invoice createdInvoice = invoiceRepository.findAll()
                .stream()
                .filter(i -> !i.getId().equals(invoice.getId()))
                .findFirst()
                .orElseThrow();

        assertThat(createdInvoice.getStatus())
                .isEqualTo(InvoiceStatus.CLOSED);

        assertThat(createdInvoice.getTotalConsumedVolume())
                .isEqualTo(BigDecimal.valueOf(85.0));

        assertThat(createdInvoice.getVolumeDifference())
                .isEqualTo(BigDecimal.valueOf(30.0));
        // =========================
        // VALIDAR MEASUREMENT CRIADA
        // =========================
        Measurement createdMeasurement = measurementRepository.findAll()
                .stream()
                .filter(m -> m.getSource() == MeasurementSource.MANUAL_APP)
                .findFirst()
                .orElseThrow();

        assertThat(createdMeasurement.getConsumedVolume())
                .isEqualTo(BigDecimal.valueOf(85.0));

        assertThat(createdMeasurement.getReader().getId())
                .isEqualTo(reader.getId());

        assertThat(createdMeasurement.getInvoice())
                .isNotNull();
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldKeepInvoiceOpen_whenBelowMinimumAndLessThanThreeManualReadings() throws Exception {

        mockMvc.perform(post("/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRequest(
                                LocalDateTime.now().plusDays(1),
                                meter.getSerialNumber(),
                                10.0,
                                reader.getId()
                        )))
                .andExpect(status().isOk());

        mockMvc.perform(post("/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRequest(
                                LocalDateTime.now().plusDays(2),
                                meter.getSerialNumber(),
                                15.0,
                                reader.getId()
                        )))
                .andExpect(status().isOk());

        Invoice createdInvoice = invoiceRepository.findAll()
                .stream()
                .filter(i -> !i.getId().equals(invoice.getId()))
                .findFirst()
                .orElseThrow();

        assertThat(createdInvoice.getStatus())
                .isEqualTo(InvoiceStatus.OPEN);
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCloseInvoice_whenThirdManualReadingEvenBelowMinimum() throws Exception {

        mockMvc.perform(post("/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRequest(
                                LocalDateTime.now().plusDays(1),
                                meter.getSerialNumber(),
                                56.0,
                                reader.getId()
                        )))
                .andExpect(status().isOk());

        mockMvc.perform(post("/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRequest(
                                LocalDateTime.now().plusDays(2),
                                meter.getSerialNumber(),
                                57.0,
                                reader.getId()
                        )))
                .andExpect(status().isOk());

        mockMvc.perform(post("/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRequest(
                                LocalDateTime.now().plusDays(3),
                                meter.getSerialNumber(),
                                58.0,
                                reader.getId()
                        )))
                .andExpect(status().isOk());

        Invoice createdInvoice = invoiceRepository.findAll()
                .stream()
                .filter(i -> !i.getId().equals(invoice.getId()))
                .findFirst()
                .orElseThrow();

        assertThat(createdInvoice.getStatus())
                .isEqualTo(InvoiceStatus.CLOSED);
        assertThat(createdInvoice.getTotalConsumedVolume())
                .isEqualTo(BigDecimal.valueOf(58.0));
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCloseInvoice_whenMinimumConsumptionIsReachedBeforeThirdReading() throws Exception {

        mockMvc.perform(post("/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRequest(
                                LocalDateTime.now().plusDays(1),
                                meter.getSerialNumber(),
                                65.0,
                                reader.getId()
                        )))
                .andExpect(status().isOk());

        mockMvc.perform(post("/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRequest(
                                LocalDateTime.now().plusDays(2),
                                meter.getSerialNumber(),
                                77.0,
                                reader.getId()
                        )))
                .andExpect(status().isOk());

        Invoice createdInvoice = invoiceRepository.findAll()
                .stream()
                .filter(i -> !i.getId().equals(invoice.getId()))
                .findFirst()
                .orElseThrow();

        assertThat(createdInvoice.getStatus())
                .isEqualTo(InvoiceStatus.CLOSED);

        assertThat(createdInvoice.getTotalConsumedVolume())
                .isEqualTo(BigDecimal.valueOf(77.0));
    }
}