package com.kosa.chanzipup.domain.payment;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {

    private Long paymentId;
    private String impUid;

    public PaymentException() {
        super();
    }

    public PaymentException(String message, String impUid, Long paymentId) {
        super(message);
        this.paymentId = paymentId;
        this.impUid = impUid;
    }

}
