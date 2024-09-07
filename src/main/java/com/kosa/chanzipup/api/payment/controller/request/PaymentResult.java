package com.kosa.chanzipup.api.payment.controller.request;

import lombok.Getter;

@Getter
public class PaymentResult {
    private String merchantUid; // 멤버십 결제 내역 UUID
    private String impUid; // PG사에서 발급해주는 결제 UUID
    private boolean success; // 결제 성공 여부
}
