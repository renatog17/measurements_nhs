package com.nhst.medicoes.service.specification;


import com.nhst.medicoes.controller.dto.meter.MeterFilter;
import com.nhst.medicoes.domain.Meter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MeterSpecification {

    public static Specification<Meter> withFilters(
            MeterFilter filter
    ) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getSerialNumber() != null
                    && !filter.getSerialNumber().isBlank()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("serialNumber")),
                                "%" + filter.getSerialNumber().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getActive() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("active"),
                                filter.getActive()
                        )
                );
            }

            if (filter.getReset() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("reset"),
                                filter.getReset()
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}