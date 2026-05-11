package com.nhst.medicoes.service;

import com.nhst.medicoes.controller.dto.property.PropertyFilter;
import com.nhst.medicoes.domain.Property;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PropertySpecification {

    public static Specification<Property> withFilters(
            PropertyFilter filter
    ) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCity() != null
                    && !filter.getCity().isBlank()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("city")),
                                "%" + filter.getCity().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getIdentifierCode() != null
                    && !filter.getIdentifierCode().isBlank()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("identifierCode")),
                                "%" + filter.getIdentifierCode().toLowerCase() + "%"
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

            if (filter.getParentPropertyId() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("parentProperty").get("id"),
                                filter.getParentPropertyId()
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}