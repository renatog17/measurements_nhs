package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Installation;
import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("""
    SELECT i
    FROM Invoice i
    WHERE i.installation = :installation
      AND i.status = 'OPEN'
""")
    Optional<Invoice> findByInstallationAndStatusOpen(Installation installation);

    @Query(value = """
    SELECT *
    FROM invoices i
    WHERE i.installation_id = :installationId
      AND i.status = 'CLOSED'
    ORDER BY i.closed_at DESC
    LIMIT 1
""", nativeQuery = true)
    Optional<Invoice> findFirstByInstallationAndStatusClosedOrderByClosedAtDesc(
            Long installationId
    );

    @Query("""
    SELECT i
    FROM Invoice i
    WHERE i.bankSlips IS EMPTY
      AND i.status = 'CLOSED'
""")
    List<Invoice> findInvoicesWithoutBankSlipAndStatusClosed();

}