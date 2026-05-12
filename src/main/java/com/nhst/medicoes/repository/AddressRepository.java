package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Address;
import com.nhst.medicoes.domain.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}