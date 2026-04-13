package com.nhst.medicoes.domain.validation;

public class CpfValidator {

    public static boolean isValid(String cpf) {

        if (cpf == null) return false;

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) return false;

        // elimina CPFs com todos dígitos iguais
        if (cpf.chars().distinct().count() == 1) return false;

        return isValidDigit(cpf, 9) && isValidDigit(cpf, 10);
    }

    private static boolean isValidDigit(String cpf, int length) {

        int sum = 0;
        int weight = length + 1;

        for (int i = 0; i < length; i++) {
            sum += (cpf.charAt(i) - '0') * weight--;
        }

        int remainder = 11 - (sum % 11);
        int digit = (remainder >= 10) ? 0 : remainder;

        return digit == (cpf.charAt(length) - '0');
    }
}