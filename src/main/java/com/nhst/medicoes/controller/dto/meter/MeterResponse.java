package com.nhst.medicoes.controller.dto.meter;

import com.nhst.medicoes.domain.Meter;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MeterResponse {

    private Long id;

    private String serialNumber;

    private BigDecimal maxVolume;

    private Integer reset;

    private boolean active;

    public static MeterResponse fromEntity(Meter meter) {

        return MeterResponse.builder()
                .id(meter.getId())
                .serialNumber(meter.getSerialNumber())
                .maxVolume(meter.getMaxVolume())
                .reset(meter.getReset())
                .active(meter.isActive())
                .build();
    }
}