package com.kosa.chanzipup.api.review.controller.response;

import lombok.Getter;

@Getter
public class ReviewImageResponse {
    private String imageUrl;

    public ReviewImageResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
