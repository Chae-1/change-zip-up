package com.kosa.chanzipup.api.membership.controller.response;

import com.kosa.chanzipup.domain.membership.MemberShip;
import com.kosa.chanzipup.domain.membership.MemberShipType;
import lombok.Getter;

@Getter
public class MemberShipResponse {
    private Long memberShipId;
    private MemberShipType type;
    private int price;

    public MemberShipResponse(MemberShip memberShip) {
        memberShipId = memberShip.getId();
        type = memberShip.getType();
        price = memberShip.getPrice();
    }
}
