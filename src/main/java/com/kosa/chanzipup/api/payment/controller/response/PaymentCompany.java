package com.kosa.chanzipup.api.payment.controller.response;

import com.kosa.chanzipup.domain.account.company.Company;
import lombok.Getter;

@Getter
public class PaymentCompany {

    private String companyEmail;
    private String companyOwner;
    private String companyAddr;
    private String postcode;

    public PaymentCompany(String companyEmail, String companyCeoName, String companyAddr, String postcode) {
        this.companyEmail = companyEmail;
        this.companyOwner = companyCeoName;
        this.companyAddr = companyAddr;
        this.postcode = postcode;
    }

    public static PaymentCompany of(Company company) {
        return new PaymentCompany(company.getEmail(), company.getOwner(), company.getAddress(), "1010");
    }
}
