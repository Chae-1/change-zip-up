package com.kosa.chanzipup.api.memberships.controller.response;

import com.kosa.chanzipup.domain.membership.Membership;
import com.kosa.chanzipup.domain.membership.MembershipType;
import lombok.Getter;

@Getter
public class MembershipHistories {
    private int price;
    private String typeName;

    public MembershipHistories(Membership membership) {
        MembershipType membershipType = membership.getMembershipType();
        this.price = membershipType.getPrice();

    }
}
