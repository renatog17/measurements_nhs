package com.nhst.medicoes.domain;

import com.nhst.medicoes.domain.enums.InvoiceStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private MeterProperty meterProperty;

    @OneToMany(mappedBy = "invoice")
    private List<Measurement> measurements;

    private BigDecimal totalValue = BigDecimal.ZERO;

    private LocalDate referenceMonth;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.OPEN;

    private LocalDateTime closedAt;
    private LocalDateTime createdAt = LocalDateTime.now();
}