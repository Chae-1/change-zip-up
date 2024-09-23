package com.kosa.chanzipup.api.estimate.controller.response;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.estimate.Estimate;

import java.util.Map;

import lombok.Getter;

import static java.util.stream.Collectors.*;

@Getter
public class EstimateDetailResponse {

    private Long estimateId;

    private String companyName;
    private String companyLogoUrl;
    private String phoneNumber;
    private String companyDesc;
    private double rating;
    private String companyAddress;
    private String companyEmail;


    private Map<String, Integer> constructionPrices;
    private int totalPrice;
    private Long countOfCompleteEstimate;


    public EstimateDetailResponse(Estimate estimate, Long countOfCompleteEstimate) {
        Company company = estimate.getCompany();
        this.estimateId = estimate.getId();
        this.phoneNumber = company.getPhoneNumber();
        this.companyLogoUrl = company.getCompanyLogoUrl();
        this.companyName = company.getCompanyName();
        this.companyDesc = company.getCompanyDesc();
        this.companyAddress = company.getAddress();
        this.rating = company.getRating();
        this.companyEmail = company.getEmail();

        this.constructionPrices = estimate
                .getEstimatePrices()
                .stream()
                .collect(toMap(price -> price.getConstructionType().getTypeName(), price -> price.getPrice()));

        this.totalPrice = constructionPrices
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        this.countOfCompleteEstimate = countOfCompleteEstimate;
    }


}
