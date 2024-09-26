package com.kosa.chanzipup.api.company.controller.response;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CompanyListResponse {
    private Long id;

    private String companyName;

    private String companyDesc;

    private String companyLogoUrl;

    private double rating;

    private List<String> services;

    public CompanyListResponse(Company company) {
        this.id = company.getId();
        this.companyName = company.getCompanyName();
        this.companyDesc = company.getCompanyDesc();
        this.companyLogoUrl = company.getCompanyLogoUrl();
        this.rating = company.getRating();
        this.services = company.getConstructionTypes()
                .stream()
                .map(CompanyConstructionType::getName)
                .toList();
    }

    public CompanyListResponse(Company company, List<CompanyConstructionType> companyConstructionTypes) {
        this.id = company.getId();
        this.companyName = company.getCompanyName();
        this.companyDesc = company.getCompanyDesc();
        this.companyLogoUrl = company.getCompanyLogoUrl();
        this.rating = company.getRating();
        this.services = companyConstructionTypes
                .stream()
                .map(type -> type.getConstructionType().getName())
                .toList();
    }
}
