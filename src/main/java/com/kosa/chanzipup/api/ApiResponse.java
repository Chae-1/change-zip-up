package com.kosa.chanzipup.api;

import com.kosa.chanzipup.api.membership.controller.response.MemberShipResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class ApiResponse<T> {

    private T data;
    private HttpStatus httpStatus;
    private int code;

    public ApiResponse(T data, HttpStatus httpStatus) {
        this.data = data;
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
    }

    public static <T> ApiResponse<T> of(T data, HttpStatus httpStatus) {
        return new ApiResponse<>(data, httpStatus);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, HttpStatus.OK);
    }
}
