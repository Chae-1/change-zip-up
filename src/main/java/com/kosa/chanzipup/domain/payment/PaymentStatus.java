package com.kosa.chanzipup.domain.payment;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentStatus {
    CREATE("결제 생성"),
    CANCEL("결제 취소"),
    COMPLETE("결제 완료");

    private final String text;
}
