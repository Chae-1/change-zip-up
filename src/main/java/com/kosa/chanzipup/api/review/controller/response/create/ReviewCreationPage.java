package com.kosa.chanzipup.api.review.controller.response.create;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.estimate.Estimate;
import com.kosa.chanzipup.domain.estimate.EstimateConstructionType;
import com.kosa.chanzipup.domain.estimate.EstimateRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ReviewCreationPage {
    private String companyName;

    private int floor;

    private int totalPrice;

    private List<BuildingTypeResponse> buildingTypeResponses;

    private List<ConstructionTypeResponse> constructionTypeResponses;

    public ReviewCreationPage(List<BuildingTypeResponse> buildingTypeResponses,
                              List<ConstructionTypeResponse> constructionTypes) {
        this.buildingTypeResponses = buildingTypeResponses;
        this.constructionTypeResponses = constructionTypes;
    }

    public ReviewCreationPage(EstimateRequest reviewRequest, Estimate reviewEstimate) {
        Company company = reviewEstimate.getCompany();
        this.companyName = company.getCompanyName();

        this.floor = reviewRequest.getFloor();
        this.totalPrice = reviewEstimate.getTotalPrices();

        this.buildingTypeResponses = new ArrayList<>();
        buildingTypeResponses.add(new BuildingTypeResponse(reviewRequest.getBuildingType()));

        this.constructionTypeResponses = reviewRequest.getConstructionTypes()
                .stream()
                .map(EstimateConstructionType::getConstructionType)
                .map(ConstructionTypeResponse::new)
                .toList();
    }
}
