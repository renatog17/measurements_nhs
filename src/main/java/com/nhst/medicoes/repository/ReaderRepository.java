package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long>,
        JpaSpecificationExecutor<Reader> {

    Optional<Reader> findByEmployeeCode(String employeeCode);

    boolean existsByEmployeeCode(String employeeCode);
}