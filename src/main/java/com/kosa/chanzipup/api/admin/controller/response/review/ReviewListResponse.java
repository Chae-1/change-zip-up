package com.kosa.chanzipup.api.admin.controller.response.review;

import com.kosa.chanzipup.api.review.controller.response.*;
import com.kosa.chanzipup.domain.review.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewListResponse {
    private Long id;

    private String title;

    private LocalDateTime regDate;

    private MemberForReviewResponse member;

    private CompanyForReviewResponse company;

    public ReviewListResponse(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.regDate = review.getRegDate();
        this.member = new MemberForReviewResponse(review.getMember());
        this.company = new CompanyForReviewResponse(review.getCompany());
    }
}
