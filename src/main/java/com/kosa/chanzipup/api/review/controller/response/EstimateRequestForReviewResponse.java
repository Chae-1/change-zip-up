package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.domain.estimate.EstimateRequest;
import lombok.Getter;

@Getter
public class EstimateRequestForReviewResponse {

    private String buildingType; // 거주 형태

    private int floor;    // 평수

    public EstimateRequestForReviewResponse(EstimateRequest estimateRequest) {
        this.buildingType = estimateRequest.getBuildingType().getName();
        this.floor = estimateRequest.getFloor();
    }
}
