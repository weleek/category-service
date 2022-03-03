package com.shop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionDefinition {

    IllegalStateException(HttpStatus.OK, "0001", "요청 대상은 변경할 수 없는 상태 입니다."),
    DataNotFoundException(HttpStatus.OK, "0002", "데이터가 존재하지 않습니다."),
    IllegalArgumentException(HttpStatus.OK, "0003", "잘못된 요청 데이터 입니다."),
    BindException(HttpStatus.BAD_REQUEST, "0004", null),
    MissingServletRequestParameterException(HttpStatus.BAD_REQUEST, "0004", null),
    MethodArgumentTypeMismatchException(HttpStatus.BAD_REQUEST, "0004", null),
    ConstraintViolationException(HttpStatus.BAD_REQUEST, "0004", null),
    UnknownException(HttpStatus.INTERNAL_SERVER_ERROR, "9999", "알수 없는 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private String message;

    public static ExceptionDefinition of(Exception exception) {
        try {
            return ExceptionDefinition.valueOf(exception.getClass().getSimpleName());
        } catch (IllegalArgumentException e) {
            return ExceptionDefinition.UnknownException;
        }
    }
}
