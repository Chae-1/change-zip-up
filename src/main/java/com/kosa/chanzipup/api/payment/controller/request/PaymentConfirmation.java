package com.kosa.chanzipup.api.payment.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentConfirmation {

    @NotBlank(message = "멤버십 결제 ID는 필수입니다.")
    private String merchantUid; // 멤버십 결제 내역 UUID
    @NotBlank(message = "결제 ID는 필수입니다.")
    private String impUid; // PG사에서 발급해주는 결제 UUID

    @NotNull(message = "성공 여부는 필수입니다.")
    private Boolean success; // 결제 성공 여부

    // 성공했을 때만 넘어온다.
    private Integer paidAmount; // 실제 결제 금액
}
