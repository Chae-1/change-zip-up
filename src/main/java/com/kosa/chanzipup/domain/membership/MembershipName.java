package com.kosa.chanzipup.domain.membership;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MembershipName {
    NO(0),
    BASIC(10000),
    PREMIUM(20000);

    private final int price;
}
