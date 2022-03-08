package com.shop.handler;

import com.shop.common.response.ApiResponse;
import com.shop.exception.ApiException;
import com.shop.exception.ExceptionDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@SuppressWarnings({"unchecked","rawtypes"})
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception exception) {
        return ResponseEntity.status(ExceptionDefinition.UnknownException.getStatus())
                .body(ApiResponse.fail(ExceptionDefinition.UnknownException.getCode(), exception.getMessage()));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> exceptionHandler(ApiException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(ApiResponse.fail(exception));
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<?> exceptionHandler(BindException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> "['" + fieldError.getField() + ":" + fieldError.getDefaultMessage() + "]")
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(ExceptionDefinition.BindException.getStatus())
                .body(ApiResponse.fail(ExceptionDefinition.BindException.getCode(), message));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> exceptionHandler(MissingServletRequestParameterException e) {
        return ResponseEntity.status(ExceptionDefinition.MissingServletRequestParameterException.getStatus())
                .body(ApiResponse.fail(ExceptionDefinition.MissingServletRequestParameterException.getCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> exceptionHandler(MethodArgumentTypeMismatchException e) {
        String message = "['" + e.getName() + "' is not " + e.getValue().getClass().getSimpleName() + "]";
        return ResponseEntity.status(ExceptionDefinition.MethodArgumentTypeMismatchException.getStatus())
                .body(ApiResponse.fail(ExceptionDefinition.MethodArgumentTypeMismatchException.getCode(), message));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> exceptionHandler(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations()
                .stream()
                .map(violation -> "['" + getPopertyName(violation.getPropertyPath().toString()) + "' is '" +
                        violation.getInvalidValue() + "': " + violation.getMessage() + "]")
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(ExceptionDefinition.MethodArgumentTypeMismatchException.getStatus())
                .body(ApiResponse.fail(ExceptionDefinition.MethodArgumentTypeMismatchException.getCode(), message));
    }

    protected String getPopertyName(String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }
}
