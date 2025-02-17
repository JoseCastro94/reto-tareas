package com.app.reto.response;

import java.time.LocalDateTime;

public class GenericApiResponse<T> {
    
    private LocalDateTime timestamp;
    private int status;
    private T data;
    private String message;

    public GenericApiResponse(int status, T data, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}