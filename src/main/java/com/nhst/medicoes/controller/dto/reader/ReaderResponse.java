package com.nhst.medicoes.controller.dto.reader;

import com.nhst.medicoes.domain.Reader;
import lombok.Builder;
import lombok.Getter;

import java.awt.event.ActionListener;
import java.time.LocalDateTime;

@Getter
@Builder
public class ReaderResponse {

    private Long id;
    private String name;
    private String employeeCode;
    private LocalDateTime createdAt;

    public static ReaderResponse fromEntity(Reader reader){
        return ReaderResponse.builder()
                .id(reader.getId())
                .name(reader.getName())
                .employeeCode(reader.getEmployeeCode())
                .createdAt(reader.getCreatedAt())
                .build();
    }

}
