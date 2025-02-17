package com.app.reto.exceptions;

public class CustomBadCredentialsException extends RuntimeException {
    private String customMessage;

    public CustomBadCredentialsException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}
