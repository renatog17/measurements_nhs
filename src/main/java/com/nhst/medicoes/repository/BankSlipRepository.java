package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.BankSlip;
import com.nhst.medicoes.domain.Meter;
import com.nhst.medicoes.domain.enums.BankSlipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankSlipRepository extends JpaRepository<BankSlip, Long> {

    List<BankSlip> findByStatusAndPaidFalse(
            BankSlipStatus status
    );
}