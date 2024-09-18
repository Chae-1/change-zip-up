package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.domain.account.company.Company;
import lombok.Getter;

@Getter
public class CompanyForReviewResponse {

    private String companyName;

    public CompanyForReviewResponse(Company company) {
        this.companyName = company.getCompanyName();
    }
}
