package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.api.review.controller.response.create.ConstructionTypeResponse;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import lombok.Getter;

import java.util.List;

@Getter
public class CompanyMyPage {
    private String companyName;
    private String companyNumber;
    private String companyLogoUrl;
    private String owner;
    private String address;
    private double rating;
    private String phoneNumber;
    private String email;

    private List<Long> activeServices;
    private List<ConstructionTypeResponse> allServices;

    public CompanyMyPage(Company company, List<ConstructionType> constructionTypes) {
        this.companyNumber = company.getCompanyNumber();
        this.companyName = company.getCompanyDesc();
        this.companyLogoUrl = company.getCompanyLogoUrl();
        this.owner = company.getOwner();
        this.address = company.getAddress();
        this.rating = company.getRating();
        this.phoneNumber = company.getPhoneNumber();
        this.email = company.getEmail();

        this.activeServices = company.getConstructionTypes()
                .stream()
                .map(type -> type.getConstructionType().getId())
                .toList();

        this.allServices = constructionTypes
                .stream()
                .map(ConstructionTypeResponse::new)
                .toList();

    }
}
