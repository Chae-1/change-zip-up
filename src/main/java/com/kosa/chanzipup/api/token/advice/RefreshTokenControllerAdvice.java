package com.kosa.chanzipup.api.token.advice;

import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.domain.account.token.RefreshTokenReIssueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(RefreshTokenReIssueException.class)
    public ApiResponse<String> refreshTokenReIssueException(RefreshTokenReIssueException ex) {
        log.info("call refreshToken Advice");
        return ApiResponse.of(ex.getMessage(), HttpStatus.UNAUTHORIZED, "인증 실패");
    }
}
