package com.kosa.chanzipup.domain.membershipinternal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberShipType {
    BASIC(100),
    PLATINUM(150);

    private final int price;
}
