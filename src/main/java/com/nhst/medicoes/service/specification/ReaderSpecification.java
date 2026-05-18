package com.nhst.medicoes.service.specification;


import com.nhst.medicoes.controller.dto.reader.ReaderFilter;
import com.nhst.medicoes.domain.Reader;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReaderSpecification {

    public static Specification<Reader> withFilters(ReaderFilter filter) {

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

            if (filter.getEmployeeCode() != null
                    && !filter.getEmployeeCode().isBlank()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("employeeCode")),
                                "%" + filter.getEmployeeCode().toLowerCase() + "%"
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}