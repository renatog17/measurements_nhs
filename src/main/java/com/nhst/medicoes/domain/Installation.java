package com.nhst.medicoes.domain;

import com.nhst.medicoes.clock.AppTime;
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
@Table(name = "installations")
public class Installation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Client client;

    @ManyToOne(optional = false)
    private Property property;

    @ManyToOne(optional = false)
    private Meter meter;

    @Builder.Default
    private boolean active = true;

    private LocalDateTime assignedAt;

    private LocalDateTime unassignedAt;

   // private BigDecimal volumeAtAssigned;

    public void deactivate(LocalDateTime time) {
        this.active = false;
        this.unassignedAt = time;
    }
}