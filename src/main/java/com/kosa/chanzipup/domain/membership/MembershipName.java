package com.kosa.chanzipup.domain.membership;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MembershipName {
    BASIC(100),
    PREMIUM(150);

    private final int price;
}
