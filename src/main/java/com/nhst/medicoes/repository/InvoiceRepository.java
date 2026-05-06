package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findFirstByMeterIdAndStatusOrderByCreatedAtDesc(
            Long meterId,
            InvoiceStatus status
    );

    @Query("""
        SELECT i
        FROM Invoice i
        WHERE NOT EXISTS (
            SELECT 1
            FROM BankSlip b
            WHERE b.invoice.id = i.id
        )
    """)
    List<Invoice> findInvoicesWithoutBankSlip();
}