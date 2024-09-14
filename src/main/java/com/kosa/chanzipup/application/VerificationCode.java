package com.kosa.chanzipup.application;

import com.kosa.chanzipup.domain.account.Verification;
import lombok.Getter;

@Getter
public class VerificationCode {

    private String verificationCode;
    private String email;

    public VerificationCode(String verificationCode, String email) {
        this.verificationCode = verificationCode;
        this.email = email;
    }

    public static VerificationCode of(String verificationCode, String email) {
        return new VerificationCode(verificationCode, email);
    }
}
