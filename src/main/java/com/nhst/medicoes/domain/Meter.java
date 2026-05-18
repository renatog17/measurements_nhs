package com.nhst.medicoes.domain;

import com.nhst.medicoes.clock.AppTime;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

//    @Column(name = "actual_volume", nullable = false, precision = 12, scale = 3)
//    private BigDecimal actualVolume;

    @Column(name = "max_volume", nullable = false, precision = 12, scale = 3)
    private BigDecimal maxVolume;

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

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Installation> installations = new ArrayList<>();

    @PrePersist
    void onCreate() {
        updatedAt = createdAt;
    }

}