package com.app.reto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EstadoValidator.class)
public @interface EstadoValido {
    String message() default "El estado debe ser: Por hacer, En progreso o Completada";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
