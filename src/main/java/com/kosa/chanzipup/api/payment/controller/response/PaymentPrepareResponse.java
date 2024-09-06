package com.kosa.chanzipup.api.payment.controller.response;

import com.kosa.chanzipup.domain.membershipinternal.MemberShipType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentPrepareResponse {

    private String merchantUid;
    private MemberShipType type;
    private int price;


    private PaymentPrepareResponse(String merchantUid, MemberShipType type) {
        this.merchantUid = merchantUid;
        this.type = type;
        this.price = type.getPrice();
    }

    public static PaymentPrepareResponse of(String merchantUid, MemberShipType type) {
        return new PaymentPrepareResponse(merchantUid, type);
    }
}
