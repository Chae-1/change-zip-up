package com.kosa.chanzipup.domain.payment;

import lombok.Getter;

@Getter
public class PaymentResult {
    private boolean isSuccess;
    private Long membershipTypeId;
    private Long companyId;

    private PaymentResult(boolean isSuccess, Long membershipTypeId, Long companyId) {
        this.isSuccess = isSuccess;
        this.membershipTypeId = membershipTypeId;
        this.companyId = companyId;
    }

    public static PaymentResult of(boolean isSuccess, Long membershipTypeId, Long companyId) {
        return new PaymentResult(isSuccess, membershipTypeId, companyId);
    }
}
