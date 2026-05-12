package com.nhst.medicoes.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(unique = true)
    private String identifierCode;

    private boolean active = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Installation> meterProperties = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Installation> clientProperties = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_property_id")
    private Property parentProperty;

    @OneToMany(mappedBy = "parentProperty")
    private List<Property> childProperties = new ArrayList<>();

    public Property(Address address, String identifierCode, String name) {
        this.address = address;
        this.name = name;
        this.identifierCode = identifierCode;
        this.active = true;
    }

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