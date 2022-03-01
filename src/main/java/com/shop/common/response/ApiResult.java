package com.shop.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    private final static String SUCCESS_CODE = "0000";

    private final boolean success;
    private final String code;
    private final T response;

    public static <T> ResponseEntity<ApiResult> success(T response) {
        return ResponseEntity
                .ok(new ApiResult(true, SUCCESS_CODE, response));
    }

    public static <T> ResponseEntity<ApiResult> success() {
        return ResponseEntity
                .ok(new ApiResult(true, SUCCESS_CODE, null));
    }

    public static <T> ResponseEntity<ApiResult> fail(ApiException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ApiResult(false, exception.getCode(), exception.getMessage()));
    }

}
