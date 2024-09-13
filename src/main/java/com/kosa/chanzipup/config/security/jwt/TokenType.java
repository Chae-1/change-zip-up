package com.kosa.chanzipup.config.security.jwt;

public enum TokenType {
    ACCESS,
    REFRESH;

    public String changeToken(String token) {
        if (this == ACCESS) {
            return String.format("Bearer %s", token);
        }
        return token;
    }
}
