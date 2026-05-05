package com.nhst.medicoes.exception;

public class PendingMeterReadingException extends RuntimeException {
    public PendingMeterReadingException(String s) {
        super("Não é possível trocar o medidor sem realizar a leitura do anterior");
    }
}