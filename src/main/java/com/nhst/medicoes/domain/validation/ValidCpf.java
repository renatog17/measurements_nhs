package com.nhst.medicoes.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfValidatorConstraint.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCpf {

    String message() default "Invalid CPF";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}