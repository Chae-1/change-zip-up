package com.kosa.chanzipup.api.company.controller.response;

import lombok.Getter;

@Getter
public class CompanyRegisterResponse {
    private String email;

    private String companyName;

    private CompanyRegisterResponse(String email, String companyName) {
        this.email = email;
        this.companyName = companyName;
    }

    public static CompanyRegisterResponse of(String email, String companyName) {
        return new CompanyRegisterResponse(email, companyName);
    }
}
