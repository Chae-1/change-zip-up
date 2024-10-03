package com.kosa.chanzipup.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class ApiResponse<T> {

    private T data;

    private HttpStatus httpStatus;
    private String message;
    private int code;

    public ApiResponse(T data, HttpStatus httpStatus, String message) {
        this.data = data;
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.message = message;
    }

    public static <T> ApiResponse<T> of(T data, HttpStatus httpStatus, String message) {
        return new ApiResponse<>(data, httpStatus, message);
    }

    public static <T> ApiResponse<T> ok(T data) {
        HttpStatus status = HttpStatus.OK;
        return new ApiResponse<>(data, status, status.getReasonPhrase());
    }
}
