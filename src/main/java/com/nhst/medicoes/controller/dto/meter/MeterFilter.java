package com.nhst.medicoes.controller.dto.meter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeterFilter {

    private String serialNumber;

    private Boolean active;

    private Integer reset;
}