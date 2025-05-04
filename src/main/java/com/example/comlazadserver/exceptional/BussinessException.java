package com.example.comlazadserver.exceptional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BussinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;
    public BussinessException(String message) {
        super(message);
        this.message = message;
    }
}
