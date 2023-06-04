package com.codetets.atm.exception;

import org.springframework.http.HttpStatus;

public class CashDispenserException extends RuntimeException {
    private HttpStatus status;

    public CashDispenserException(String message) {
        super(message);
    }

    public CashDispenserException(String message, IllegalArgumentException e) {
        super(message, e);
    }

    public CashDispenserException(String message, HttpStatus status) {
        super(message);
        this.status =  status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}