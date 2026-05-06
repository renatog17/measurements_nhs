package com.nhst.medicoes.boletogateway;

import com.nhst.medicoes.domain.bank.BankBoletoRegisterRequest;
import com.nhst.medicoes.domain.bank.BankBoletoRegisterResponse;

public interface BoletoGateway {

    BankBoletoRegisterResponse register(BankBoletoRegisterRequest request);

}