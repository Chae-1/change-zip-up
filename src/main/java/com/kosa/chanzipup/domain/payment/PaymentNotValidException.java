package com.kosa.chanzipup.domain.payment;

public class PaymentNotValidException extends PaymentException {
    public PaymentNotValidException(String message, String impUid, Long paymentId) {
        super(message, impUid, paymentId);
    }
}
