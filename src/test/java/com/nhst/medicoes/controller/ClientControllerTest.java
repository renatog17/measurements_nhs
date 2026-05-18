package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.domain.enums.PersonType;
import com.nhst.medicoes.repository.ClientRepository;
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
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCreateClient() throws Exception {

        String json = """
        {
            "name": "José da Silva",
            "email": "jose@email.com",
            "document": "12345678901",
            "personType": "PHYSICAL",
            "addressRequest": {
                "street": "Rua das Flores",
                "number": "123",
                "complement": "Apto 101",
                "neighborhood": "Centro",
                "city": "Salvador",
                "state": "BA",
                "zipCode": "40000-000"
            }
        }
        """;

        mockMvc.perform(
                        post("/clients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated());

        Client client = clientRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        assertThat(client.getId()).isNotNull();

        assertThat(client.getName())
                .isNotNull()
                .isEqualTo("José da Silva");

        assertThat(client.getEmail())
                .isNotNull()
                .isEqualTo("jose@email.com");

        assertThat(client.getDocument())
                .isNotNull()
                .isEqualTo("12345678901");

        assertThat(client.getPersonType())
                .isEqualTo(PersonType.PHYSICAL);

        assertThat(client.isActive()).isTrue();

        assertThat(client.getCreatedAt()).isNotNull();

        assertThat(client.getAddress()).isNotNull();

        assertThat(client.getAddress().getStreet())
                .isEqualTo("Rua das Flores");

        assertThat(client.getAddress().getNumber())
                .isEqualTo("123");

        assertThat(client.getAddress().getComplement())
                .isEqualTo("Apto 101");

        assertThat(client.getAddress().getNeighborhood())
                .isEqualTo("Centro");

        assertThat(client.getAddress().getCity())
                .isEqualTo("Salvador");

        assertThat(client.getAddress().getState())
                .isEqualTo("BA");

        assertThat(client.getAddress().getZipCode())
                .isEqualTo("40000-000");
    }
}