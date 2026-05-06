package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.BankSlip;
import com.nhst.medicoes.domain.Meter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankSlipRepository extends JpaRepository<BankSlip, Long> {
}