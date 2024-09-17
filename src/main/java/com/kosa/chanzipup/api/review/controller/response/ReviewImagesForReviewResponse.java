package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.domain.review.ReviewImages;
import lombok.Getter;

@Getter
public class ReviewImagesForReviewResponse {

    private Long id;

    private String imageUrl;

    public ReviewImagesForReviewResponse(ReviewImages reviewImages) {
        this.id = reviewImages.getId();
        this.imageUrl = reviewImages.getImageUrl();
    }
}
