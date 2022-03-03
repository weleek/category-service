package com.shop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final ExceptionDefinition error;
    private final String customMessage;

    public ApiException(ExceptionDefinition error) {
        this.error = error;
        this.customMessage = null;
    }

    public ApiException(ExceptionDefinition error, String customMessage) {
        this.error = error;
        this.customMessage = customMessage;
    }

    public HttpStatus getHttpStatus() {
        return this.error.getStatus();
    }

    public String getCode() {
        return this.error.getCode();
    }

    public String getMessage() {
        return this.customMessage != null ?
                this.customMessage : this.error.getMessage();
    }
}
