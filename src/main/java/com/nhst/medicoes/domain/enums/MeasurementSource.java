package com.nhst.medicoes.domain.enums;

public enum MeasurementSource {

    INITIALIZATION,//quando o próprio sistema faz a leitura inicial de uma nova relação
    MANUAL_APP,   // digitado pelo leiturista no celular
    CAMERA_OCR,   // leitura via foto + OCR
    IMPORT,       // importação de arquivo/sistema externo
    SENSOR_IOT    // leitura automática (futuro)
}