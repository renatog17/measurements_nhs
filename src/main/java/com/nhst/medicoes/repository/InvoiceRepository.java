package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Invoice;
import com.nhst.medicoes.domain.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findFirstByMeterPropertyIdAndStatusOrderByCreatedAtDesc(
            Long meterPropertyId,
            InvoiceStatus status
    );
}