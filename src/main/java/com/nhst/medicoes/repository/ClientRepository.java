package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}