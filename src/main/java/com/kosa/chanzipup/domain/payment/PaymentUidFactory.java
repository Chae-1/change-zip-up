package com.kosa.chanzipup.domain.payment;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentUidFactory {

    public String createNextPaymentId() {
        return UUID.randomUUID().toString();
    }
}
