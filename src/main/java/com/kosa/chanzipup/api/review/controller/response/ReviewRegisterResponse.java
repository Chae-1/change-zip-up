package com.kosa.chanzipup.api.review.controller.response;

import lombok.Getter;

@Getter
public class ReviewRegisterResponse {

    private final Long id;

    private ReviewRegisterResponse(Long id) {
        this.id = id;
    }

    public static ReviewRegisterResponse of(Long id) {
        return new ReviewRegisterResponse(id);
    }

}
