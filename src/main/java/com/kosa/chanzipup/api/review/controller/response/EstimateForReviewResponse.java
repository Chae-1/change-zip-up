package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.domain.estimate.Estimate;
import lombok.Getter;

@Getter
public class EstimateForReviewResponse {

    private Long totalPrice;

    public EstimateForReviewResponse(Estimate estimate) {
        this.totalPrice = estimate.getTotalPrice();
    }
}
