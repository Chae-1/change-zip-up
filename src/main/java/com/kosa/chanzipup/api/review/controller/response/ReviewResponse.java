package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.review.Review;
import com.kosa.chanzipup.domain.review.ReviewConstructionType;
import com.kosa.chanzipup.domain.review.ReviewImages;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class ReviewResponse {

    // 필요 리뷰 정보
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regDate;  // 게시일
    private LocalDate workStartDate;
    private LocalDate workEndDate;
    private int rating;
    private int floor;


    // 회사 정보
    private Long companyId;
    private String companyName;


    // 멤버 정보
    private String memberNickName;
    private List<ReviewImageResponse> reviewImageResponses;

    private List<String> constructionTypes;


    public ReviewResponse(Review review, Company company, Member member,
                          List<ReviewImages> images, List<ReviewConstructionType> reviewConstructionTypes) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.regDate = review.getRegDate();
        this.workStartDate = review.getWorkStartDate();
        this.workEndDate = review.getWorkEndDate();
        this.floor = review.getFloor();
        this.rating = review.getRating();

        this.companyId = company.getId();
        this.companyName = company.getCompanyName();

        this.memberNickName = member.getNickName();
        this.reviewImageResponses = images == null ? Collections.emptyList() : images
                .stream()
                .map(image -> new ReviewImageResponse(image.getImageUrl()))
                .toList();
        this.constructionTypes = reviewConstructionTypes.stream()
                .map(type -> type.getConstructionType().getName())
                .toList();

    }
}
