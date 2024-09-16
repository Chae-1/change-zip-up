package com.kosa.chanzipup.api.review.controller.response;

import lombok.Getter;

@Getter
public class ReviewRegisterResponse {
    private Long reviewId;

    public ReviewRegisterResponse(Long reviewId) {
        this.reviewId = reviewId;
    }
}
