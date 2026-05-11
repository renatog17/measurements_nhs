package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.Client;
import com.nhst.medicoes.controller.dto.client.ClientFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ClientSpecification {

    public static Specification<Client> withFilters(ClientFilter filter) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + filter.getName().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getEmail() != null && !filter.getEmail().isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("email")),
                                "%" + filter.getEmail().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getCpf() != null && !filter.getCpf().isBlank()) {
                predicates.add(
                        criteriaBuilder.equal(root.get("cpf"), filter.getCpf())
                );
            }

            if (filter.getActive() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("active"), filter.getActive())
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}