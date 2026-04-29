package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Optional<Reader> findByEmployeeCode(String employeeCode);

    boolean existsByEmployeeCode(String employeeCode);
}