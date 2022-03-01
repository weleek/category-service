package com.shop.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final ExceptionDefinition error;
    private final String customMessage;

    public ApiException (ExceptionDefinition e) {
        super(e.getMessage());
        this.error = e;
        this.customMessage = e.getMessage();
    }

    public ApiException (ExceptionDefinition e, String message) {
        super(message);
        this.error = e;
        this.customMessage = message;
    }

    public HttpStatus getHttpStatus() {
        return this.error.getStatus();
    }

    public String getCode() {
        return this.error.getCode();
    }

    public String getMessage() {
        return this.customMessage;
    }
}
