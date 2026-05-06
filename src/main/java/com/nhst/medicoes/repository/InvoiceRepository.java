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
        select i from Invoice i
        where i.status = 'CLOSED'
        and not exists (
            select bs from BankSlip bs
            where bs.invoice = i and bs.active = true
        )
    """)
    List<Invoice> findClosedWithoutActiveBankSlip();
}