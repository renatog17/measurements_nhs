package com.nhst.medicoes.domain;

import com.nhst.medicoes.domain.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Meter meter;

    @OneToMany(mappedBy = "invoice")
    private List<Measurement> measurements = new ArrayList<Measurement>();

    private BigDecimal totalConsumedVolume;
    private BigDecimal pricePerM3;

    private LocalDate referenceMonth = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.OPEN;

    private LocalDateTime closedAt;
    private LocalDateTime createdAt = LocalDateTime.now();
}