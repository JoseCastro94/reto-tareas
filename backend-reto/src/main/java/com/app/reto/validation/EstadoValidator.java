package com.app.reto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EstadoValidator implements ConstraintValidator<EstadoValido, String> {

    private static final String[] ESTADOS_VALIDOS = {"Por hacer", "En progreso", "Completada"};

    @Override
    public boolean isValid(String estado, ConstraintValidatorContext context) {
        if (estado == null || estado.isEmpty()) {
            return false; 
        }
        for (String validEstado : ESTADOS_VALIDOS) {
            if (estado.equalsIgnoreCase(validEstado)) {
                return true;
            }
        }
        return false;
    }
}