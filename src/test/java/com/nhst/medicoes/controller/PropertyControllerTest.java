package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.Property;
import com.nhst.medicoes.domain.enums.PropertyType;
import com.nhst.medicoes.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCreateProperty() throws Exception {

        String json = """
            {
                "name": "Residencial Primavera",
                "type": "RESIDENTIAL",
                "addressRequest": {
                    "street": "Rua das Palmeiras",
                    "number": "120",
                    "complement": "Bloco A",
                    "neighborhood": "Centro",
                    "city": "Salvador",
                    "state": "BA",
                    "zipCode": "40000-000"
                },
                "parentPropertyId": null
            }
            """;

        mockMvc.perform(
                        post("/properties")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated());

        Property property = propertyRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        assertThat(property.getId()).isNotNull();

        assertThat(property.getName())
                .isNotNull()
                .isEqualTo("Residencial Primavera");

        assertThat(property.getType())
                .isEqualTo(PropertyType.RESIDENTIAL);

        assertThat(property.isActive()).isTrue();

        assertThat(property.getCreatedAt()).isNotNull();

        assertThat(property.getParentProperty()).isNull();

        assertThat(property.getAddress()).isNotNull();

        assertThat(property.getAddress().getStreet())
                .isEqualTo("Rua das Palmeiras");

        assertThat(property.getAddress().getNumber())
                .isEqualTo("120");

        assertThat(property.getAddress().getComplement())
                .isEqualTo("Bloco A");

        assertThat(property.getAddress().getNeighborhood())
                .isEqualTo("Centro");

        assertThat(property.getAddress().getCity())
                .isEqualTo("Salvador");

        assertThat(property.getAddress().getState())
                .isEqualTo("BA");

        assertThat(property.getAddress().getZipCode())
                .isEqualTo("40000-000");
    }
}