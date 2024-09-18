package com.kosa.chanzipup.api.review.controller.response;

import lombok.Getter;

@Getter
public class ReviewConstructionTypeResponse {
    private String constructionType;

    public ReviewConstructionTypeResponse(String constructionType) {
        this.constructionType = constructionType;
    }
}
