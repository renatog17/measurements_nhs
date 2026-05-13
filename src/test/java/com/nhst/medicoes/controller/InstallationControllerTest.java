package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.*;
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
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class InstallationControllerTest {

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
    }

    // =========================
    // CASOS VÁLIDOS
    // =========================

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCreateInstallation_withPropertyAndClient_only() throws Exception {

        String json = """
        {
          "clientId": %d,
          "propertyId": %d,
          "readerId": %d
        }
        """.formatted(
                client.getId(),
                property.getId(),
                reader.getId()
        );

        mockMvc.perform(post("/installation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCreateInstallation_withMeterIncluded() throws Exception {

        String json = """
        {
          "meterId": %d,
          "clientId": %d,
          "propertyId": %d,
          "readerId": %d,
          "volumeAtInstallation": 0
        }
        """.formatted(
                meter.getId(),
                client.getId(),
                property.getId(),
                reader.getId()
        );

        mockMvc.perform(post("/installation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        // =========================
        // VALIDAÇÕES NO BANCO
        // =========================

        List<Installation> installations = installationRepository.findAll();
        assertThat(installations).hasSize(1);

        Installation installation = installations.get(0);

        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSize(1);

        Invoice invoice = invoices.get(0);

        assertThat(invoice.getInstallation().getId())
                .isEqualTo(installation.getId());

        List<Measurement> measurements = measurementRepository.findAll();
        assertThat(measurements).hasSize(1);

        Measurement measurement = measurements.get(0);

        assertThat(measurement.getInvoice().getId())
                .isEqualTo(invoice.getId());

        assertThat(measurement.getSource())
                .isEqualTo(MeasurementSource.INITIALIZATION);

        assertThat(measurement.getConsumedVolume())
                .isEqualByComparingTo("0");
    }

    // =========================
    // CASOS INVÁLIDOS
    // =========================

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldFail_whenOnlyClient() throws Exception {

        String json = """
        {
          "clientId": %d,
          "readerId": %d
        }
        """.formatted(
                client.getId(),
                reader.getId()
        );

        mockMvc.perform(post("/installation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldFail_whenOnlyProperty() throws Exception {

        String json = """
        {
          "propertyId": %d,
          "readerId": %d
        }
        """.formatted(
                property.getId(),
                reader.getId()
        );

        mockMvc.perform(post("/installation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldFail_whenOnlyMeter() throws Exception {

        String json = """
        {
          "meterId": %d,
          "readerId": %d
        }
        """.formatted(
                meter.getId(),
                reader.getId()
        );

        mockMvc.perform(post("/installation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldFail_whenClientAndMeterWithoutProperty() throws Exception {

        String json = """
        {
          "clientId": %d,
          "meterId": %d,
          "readerId": %d
        }
        """.formatted(
                client.getId(),
                meter.getId(),
                reader.getId()
        );

        mockMvc.perform(post("/installation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // DESATIVAR INSTALAÇÃO
    // =========================

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldDisableInstallation() throws Exception {

        Installation installation = installationRepository.saveAndFlush(
                Installation.builder()
                        .meter(meter)
                        .client(client)
                        .property(property)
                        .active(true)
                        .build()
        );

        mockMvc.perform(post("/installation/disable/{serialNumber}", meter.getSerialNumber()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldFail_whenMeterAlreadyInUse() throws Exception {

        installationRepository.saveAndFlush(
                Installation.builder()
                        .meter(meter)
                        .client(client)
                        .property(property)
                        .active(true)
                        .build()
        );

        Client anotherClient = clientRepository.saveAndFlush(
                Client.builder()
                        .name("Outro")
                        .email("outro@test.com")
                        .document("99999999999")
                        .personType(PersonType.PHYSICAL)
                        .address(property.getAddress())
                        .build()
        );

        String json = """
    {
      "meterId": %d,
      "clientId": %d,
      "propertyId": %d,
      "readerId": %d
    }
    """.formatted(
                meter.getId(),
                anotherClient.getId(),
                property.getId(),
                reader.getId()
        );

        mockMvc.perform(post("/installation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldFail_whenPropertyAlreadyInUse() throws Exception {

        installationRepository.saveAndFlush(
                Installation.builder()
                        .meter(meter)
                        .client(client)
                        .property(property)
                        .active(true)
                        .build()
        );

        Meter anotherMeter = meterRepository.saveAndFlush(
                Meter.builder()
                        .serialNumber("M-002")
                        .maxVolume(new BigDecimal("99999"))
                        .reset(0)
                        .active(true)
                        .build()
        );

        String json = """
    {
      "meterId": %d,
      "clientId": %d,
      "propertyId": %d,
      "readerId": %d
    }
    """.formatted(
                anotherMeter.getId(),
                client.getId(),
                property.getId(),
                reader.getId()
        );

        mockMvc.perform(post("/installation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldAllow_sameClientInMultipleInstallations() throws Exception {

        Meter meter2 = meterRepository.saveAndFlush(
                Meter.builder()
                        .serialNumber("M-002")
                        .maxVolume(new BigDecimal("99999"))
                        .reset(0)
                        .active(true)
                        .build()
        );

        Property property2 = propertyRepository.saveAndFlush(
                Property.builder()
                        .address(property.getAddress())
                        .name("Casa 2")
                        .type(PropertyType.RESIDENTIAL)
                        .build()
        );

        // primeira instalação já existe
        installationRepository.saveAndFlush(
                Installation.builder()
                        .meter(meter)
                        .client(client)
                        .property(property)
                        .active(true)
                        .build()
        );

        String json = """
    {
      "meterId": %d,
      "clientId": %d,
      "propertyId": %d,
      "readerId": %d,
      "volumeAtInstallation": 123
    }
    """.formatted(
                meter2.getId(),
                client.getId(),
                property2.getId(),
                reader.getId()
        );

        mockMvc.perform(post("/installation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        // =========================
        // ASSERTS NO BANCO
        // =========================

        List<Installation> installations = installationRepository.findAll();

        assertThat(installations).hasSize(2);

        assertThat(installations)
                .extracting(i -> i.getClient().getId())
                .containsOnly(client.getId());
    }
}