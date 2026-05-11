package com.nhst.medicoes.repository;

import com.nhst.medicoes.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientRepository extends JpaRepository<Client, Long>,
        JpaSpecificationExecutor<Client> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

}