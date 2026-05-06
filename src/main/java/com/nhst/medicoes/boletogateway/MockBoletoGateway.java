package com.nhst.medicoes.boletogateway;

import com.nhst.medicoes.domain.bank.BankBoletoRegisterRequest;
import com.nhst.medicoes.domain.bank.BankBoletoRegisterResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class MockBoletoGateway implements BoletoGateway {

    // contador global para simular "nosso número" do banco
    private static final AtomicLong NOSSO_NUMERO_SEQ = new AtomicLong(100000);

    @Override
    public BankBoletoRegisterResponse register(BankBoletoRegisterRequest request) {

        String nossoNumero = String.valueOf(NOSSO_NUMERO_SEQ.incrementAndGet());

        return new BankBoletoRegisterResponse(
                "REGISTERED",
                nossoNumero,
                request.title().documentNumber(),
                generateBarcode(nossoNumero),
                generateDigitableLine(nossoNumero),
                request.title().amount(),
                request.title().dueDate(),
                null,
                null,
                null
        );
    }

    // ===== helpers =====

    private String generateBarcode(String nossoNumero) {
        return "34191" + nossoNumero + System.currentTimeMillis();
    }

    private String generateDigitableLine(String nossoNumero) {
        return "34191.0000" + nossoNumero + " 00000.000000 00000.000000 0 00000000000000";
    }
}