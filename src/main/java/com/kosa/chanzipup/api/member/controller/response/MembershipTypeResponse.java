package com.kosa.chanzipup.api.member.controller.response;

import com.kosa.chanzipup.domain.membership.MembershipName;
import com.kosa.chanzipup.domain.membership.MembershipType;
import lombok.Getter;

@Getter
public class MembershipTypeResponse {
    private Long id;
    private MembershipName type;
    private int price;

    public MembershipTypeResponse(MembershipType memberShipType) {
        id = memberShipType.getId();
        type = memberShipType.getName();
        price = memberShipType.getPrice();
    }
}
