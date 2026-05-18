package com.nhst.medicoes.domain;

import com.nhst.medicoes.clock.AppTime;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

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

    @Builder.Default
    @OneToMany(mappedBy = "invoice")
    private List<Measurement> measurements = new ArrayList<Measurement>();

    private BigDecimal totalConsumedVolume;
    private BigDecimal pricePerM3;

    private LocalDate referenceMonth = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.OPEN;

    private LocalDateTime closedAt;
    private LocalDateTime createdAt;
    private BigDecimal volumeDifference;
    private BigDecimal totalAmountDue;
    @ManyToOne
    @JoinColumn(name = "installation_id")
    private Installation installation;

    @Builder.Default
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankSlip> bankSlips = new ArrayList<>();

    public void closeInvoice(LocalDateTime dateTime){
        this.status = InvoiceStatus.CLOSED;
        this.closedAt = dateTime;
    }
}