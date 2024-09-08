package com.kosa.chanzipup.api.membershipinternal.controller.response;

import com.kosa.chanzipup.domain.membershipinternal.MembershipInternal;
import com.kosa.chanzipup.domain.membershipinternal.MemberShipType;
import lombok.Getter;

@Getter
public class MemberShipInternalResponse {
    private Long id;
    private MemberShipType type;
    private int price;

    public MemberShipInternalResponse(MembershipInternal memberShipInternal) {
        id = memberShipInternal.getId();
        type = memberShipInternal.getType();
        price = memberShipInternal.getPrice();
    }
}
