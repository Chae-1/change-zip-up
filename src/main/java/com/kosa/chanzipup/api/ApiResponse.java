package com.kosa.chanzipup.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class ApiResponse<T> {

    private T data;
    private HttpStatus httpStatus;
    private int code;

    public ApiResponse(T data) {
        this.data = data;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }
}
