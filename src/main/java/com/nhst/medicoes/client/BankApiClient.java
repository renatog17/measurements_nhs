package com.nhst.medicoes.client;

import com.nhst.medicoes.client.dto.ExternalBoletoRequest;
import com.nhst.medicoes.client.dto.ExternalBoletoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class BankApiClient {

    private final RestTemplate restTemplate;

    private static final String URL = "http://localhost:8081/boletos";

    public ExternalBoletoResponse createBoleto(ExternalBoletoRequest request) {
        ResponseEntity<ExternalBoletoResponse> response =
                restTemplate.postForEntity(URL, request, ExternalBoletoResponse.class);

        return response.getBody();
    }
}