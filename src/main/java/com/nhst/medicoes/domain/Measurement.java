package com.nhst.medicoes.domain;

import com.nhst.medicoes.domain.enums.MeasurementSource;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private MeterProperty meterProperty;

    @ManyToOne(optional = false)
    private Reader reader;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementSource source;

    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal value;

    @Column(nullable = false)
    private LocalDateTime measuredAt;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }
}