package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.Meter;
import com.nhst.medicoes.repository.MeterRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class MeterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MeterRepository meterRepository;

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCreateMeter() throws Exception {

        String json = """
        {
            "serialNumber": "METER-12345",
            "maxVolume": 9999.999
        }
        """;

        mockMvc.perform(
                        post("/meters")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated());

        Meter meter = meterRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        assertThat(meter.getId()).isNotNull();

        assertThat(meter.getSerialNumber())
                .isNotNull()
                .isEqualTo("METER-12345");

        assertThat(meter.getMaxVolume())
                .isNotNull()
                .isEqualByComparingTo(new BigDecimal("9999.999"));

        assertThat(meter.isActive()).isTrue();

        assertThat(meter.getCreatedAt()).isNotNull();
    }
}