package com.kosa.chanzipup.api.payment.controller.advice;

import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.api.payment.service.PaymentService;
import com.kosa.chanzipup.domain.payment.PaymentException;
import com.kosa.chanzipup.domain.payment.PaymentNotValidException;
import com.kosa.chanzipup.domain.payment.RefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class PaymentControllerAdvice {

    private final RefundService refundService;

    private final PaymentService paymentService;

    /**
     * PaymentNotValidException: 서버에 저장된 결제 내역과 PG사의 결제 내역이 다를때 발생하는 예외.
     * ex) 실제 결제 금액과 서버에서 요구하는 결제 금액과 일치하지 않을 경우
     */
    @ExceptionHandler(PaymentNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // todo: http status
    public ApiResponse<Object> paymentNotValidException(PaymentNotValidException paymentException) {
        log.info("{}, {}", paymentException.getPaymentId(), paymentException.getImpUid());
        String impUid = paymentService.cancelPayment(paymentException.getPaymentId());
        refundService.refundBy(impUid);
        return ApiResponse.of(null, HttpStatus.FORBIDDEN, paymentException.getMessage());
    }

}
