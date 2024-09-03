package com.kosa.chanzipup.domain.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AccountRole {
    USER("ROLE_USER"),
    COMPANY("ROLE_COMPANY"),
    ADMIN("ROLE_ADMIN");

    private final String role;
}
