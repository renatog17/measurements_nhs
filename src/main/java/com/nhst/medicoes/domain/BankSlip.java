package com.nhst.medicoes.domain;

import com.nhst.medicoes.domain.enums.BankSlipStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bank_slips")
public class BankSlip {

    @Id
    @GeneratedValue
    private Long id;

    private String nossoNumero;
    private String digitableLine;
    private String barcode;

    private BigDecimal amount;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private BankSlipStatus status;

    private boolean active;
    private boolean paid;
    private LocalDateTime paidAt;

    @ManyToOne
    private Invoice invoice;
}