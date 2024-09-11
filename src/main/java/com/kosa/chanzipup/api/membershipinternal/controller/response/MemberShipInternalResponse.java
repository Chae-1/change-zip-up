package com.kosa.chanzipup.api.membershipinternal.controller.response;

import com.kosa.chanzipup.domain.membershipinternal.MembershipInternal;
import com.kosa.chanzipup.domain.membershipinternal.MembershipType;
import lombok.Getter;

@Getter
public class MemberShipInternalResponse {
    private Long id;
    private MembershipType type;
    private int price;

    public MemberShipInternalResponse(MembershipInternal memberShipInternal) {
        id = memberShipInternal.getId();
        type = memberShipInternal.getType();
        price = memberShipInternal.getPrice();
    }
}
