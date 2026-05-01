package com.nhst.medicoes.domain;

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
@Table(name = "meters")
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", unique = true, nullable = false, length = 120)
    private String serialNumber;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "value", nullable = false, precision = 12, scale = 3)
    private BigDecimal value;

    @Column(name = "max_value", nullable = false, precision = 12, scale = 3)
    private BigDecimal maxValue;

    @Builder.Default
    @Column(name = "reset", nullable = false)
    private Integer reset = 0;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}