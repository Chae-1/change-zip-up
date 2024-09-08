package com.kosa.chanzipup.api.payment.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentResult {
    @NotBlank
    private String merchantUid; // 멤버십 결제 내역 UUID
    @NotBlank
    private String impUid; // PG사에서 발급해주는 결제 UUID
    @NotNull
    private Boolean success; // 결제 성공 여부
    @NotNull
    private Integer paidAmount; // 실제 결제 금액
}
