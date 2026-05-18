package com.nhst.medicoes.controller;

import com.nhst.medicoes.domain.Reader;
import com.nhst.medicoes.repository.ReaderRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ReaderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReaderRepository readerRepository;

    @Test
    @WithMockUser(roles = "OPERATOR")
    void shouldCreateReader() throws Exception {
        String json = """
        {
            "name": "José",
            "employeeCode": "j123"
        }
        """;

        mockMvc.perform(
                        post("/readers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                .andExpect(
                        status()
                                .isCreated());

        Reader reader = readerRepository.findById(1L).orElseThrow();

        assertThat(reader.getId()).isNotNull();

        assertThat(reader.getName())
                .isNotNull()
                .isEqualTo("José");

        assertThat(reader.getEmployeeCode())
                .isNotNull()
                .isEqualTo("j123");

        assertThat(reader.isActive()).isTrue();

        assertThat(reader.getCreatedAt()).isNotNull();

    }
}
