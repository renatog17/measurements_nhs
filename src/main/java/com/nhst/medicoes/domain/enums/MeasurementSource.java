package com.nhst.medicoes.domain.enums;

public enum MeasurementSource {

    MANUAL_APP,   // digitado pelo leiturista no celular
    CAMERA_OCR,   // leitura via foto + OCR
    IMPORT,       // importação de arquivo/sistema externo
    SENSOR_IOT    // leitura automática (futuro)
}