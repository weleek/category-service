package com.shop.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings({"unchecked","rawtypes"})
public class ApiResponse<T> {

    private final static String SUCCESS_CODE = "0000";

    private final boolean success;
    private final String code;
    private final T response;

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse(true, SUCCESS_CODE, response);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse(true, SUCCESS_CODE, null);
    }

    public static <T> ApiResponse<T> fail(ApiException exception) {
        return new ApiResponse(false, exception.getCode(), exception.getMessage());
    }

    public static <T> ApiResponse<T> fail(String code, T response) {
        return new ApiResponse<>(false, code ,response);
    }
}
