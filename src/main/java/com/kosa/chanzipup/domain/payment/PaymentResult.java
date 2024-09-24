package com.kosa.chanzipup.domain.payment;

import lombok.Getter;

@Getter
public class PaymentResult {
    private boolean isSuccess;
    private Long membershipTypeId;
    private Long companyId;
    private String impUid;

    private PaymentResult(boolean isSuccess, Long membershipTypeId, Long companyId, String impUid) {
        this.isSuccess = isSuccess;
        this.membershipTypeId = membershipTypeId;
        this.companyId = companyId;
        this.impUid = impUid;
    }

    public static PaymentResult of(boolean isSuccess, Long membershipTypeId, Long companyId, String impUid) {
        return new PaymentResult(isSuccess, membershipTypeId, companyId, impUid);
    }
}
