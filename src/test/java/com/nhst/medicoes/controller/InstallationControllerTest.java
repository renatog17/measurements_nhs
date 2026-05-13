package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class InstallationControllerTest {

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

    private Address address;
    private Client client;
    private Property property;
    private Meter meter;
    private Reader reader;

    @BeforeEach
    void setup() {

        address = addressRepository.saveAndFlush(
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
                        .email("renato1@test.com")
                        .document("07365657529")
                        .personType(PersonType.PHYSICAL)
                        .address(address)
                        .build()
        );

        property = propertyRepository.saveAndFlush(
                Property.builder()
                        .address(address)
                        .name("Casa Renato")
                        .type(PropertyType.RESIDENTIAL)
                        .build()
        );

        meter = meterRepository.saveAndFlush(
                Meter.builder()
                        .serialNumber("H1231")
                        .actualVolume(BigDecimal.ZERO)
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

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCreateInstallation() throws Exception {

        String json = """
                {
                  "meterId": %d,
                  "clientId": %d,
                  "propertyId": %d,
                  "volumeAtAssigned": 0,
                  "readerId": %d
                }
                """.formatted(
                meter.getId(),
                client.getId(),
                property.getId(),
                reader.getId()
        );

        mockMvc.perform(
                        post("/installation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldDisableInstallation() throws Exception {

        installationRepository.saveAndFlush(
                Installation.builder()
                        .meter(meter)
                        .client(client)
                        .property(property)
                        .active(true)
                        .build()
        );

        mockMvc.perform(
                        post("/installation/disable/{serialNumber}", meter.getSerialNumber())
                )
                .andExpect(status().isOk());
    }
}