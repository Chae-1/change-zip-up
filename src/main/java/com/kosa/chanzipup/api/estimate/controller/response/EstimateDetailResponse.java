package com.kosa.chanzipup.api.estimate.controller.response;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.estimate.Estimate;
import com.kosa.chanzipup.domain.estimate.EstimatePrice;
import com.kosa.chanzipup.domain.estimate.EstimateStatus;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Getter
public class EstimateDetailResponse {

    private String companyName;
    private String companyLogoUrl;
    private double rating;

    private Map<String, Integer> constructionPrices;
    private int totalPrice;

    public EstimateDetailResponse(Estimate estimate) {
        Company company = estimate.getCompany();
        this.companyLogoUrl = company.getCompanyLogoUrl();
        this.companyName = company.getCompanyName();
        this.rating = company.getRating();
        this.constructionPrices = estimate
                .getEstimatePrices()
                .stream()
                .collect(toMap(price -> price.getConstructionType().getTypeName(), price -> price.getPrice()));

        this.totalPrice = constructionPrices
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }


}
