package com.nhst.medicoes.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "client_properties")
public class ClientProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Client client;

    @ManyToOne(optional = false)
    private Property property;

    @Builder.Default
    private boolean active = true;

    private LocalDateTime assignedAt;

    private LocalDateTime unassignedAt;

    @PrePersist
    void onCreate() {
        assignedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.unassignedAt = LocalDateTime.now();
    }
}