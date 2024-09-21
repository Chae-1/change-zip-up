package com.kosa.chanzipup.api.estimate.controller.response;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.estimate.Estimate;
import com.kosa.chanzipup.domain.estimate.EstimatePrice;
import com.kosa.chanzipup.domain.estimate.EstimateStatus;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class SimpleEstimateResponse {
    private Long estimateId;
    private String companyName;
    private String companyLogoUrl;
    private double rating;

    private int completeEstimateCount;

    private int totalPrice;

    public SimpleEstimateResponse(Estimate estimate, Map<Company, List<Estimate>> companyEstimates) {
        this.estimateId = estimate.getId();

        Company company = estimate.getCompany();
        this.companyLogoUrl = company.getCompanyLogoUrl();
        this.companyName = company.getCompanyName();
        this.rating = company.getRating();

        this.completeEstimateCount = companyEstimates
                .get(company)
                .stream()
                .filter(e -> e.getEstimateStatus() == EstimateStatus.ACCEPTED)
                .toList()
                .size();

        this.totalPrice = estimate.getEstimatePrices()
                .stream()
                .mapToInt(EstimatePrice::getPrice)
                .sum();
    }
}
