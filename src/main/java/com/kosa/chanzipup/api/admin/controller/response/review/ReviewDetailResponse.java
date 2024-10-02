package com.kosa.chanzipup.api.admin.controller.response.review;

import com.kosa.chanzipup.api.review.controller.response.ReviewConstructionTypeResponse;
import com.kosa.chanzipup.api.review.controller.response.ReviewImageResponse;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewDetailResponse {

    private Long reviewId;

    private String content;

    private String title;

    private LocalDateTime regDate;

    private LocalDate workStartDate;

    private LocalDate workEndDate;

    private int rating;

    private Long totalPrice;

    private int floor;

    private String companyName;

    private String memberNickName;

    private List<ReviewImageResponse> reviewImageResponses;

    private List<ReviewConstructionTypeResponse> reviewConstructionTypeResponses;

    private String buildingTypeName;

    public ReviewDetailResponse(Long reviewId, String content, String title, LocalDateTime regDate, LocalDate workStartDate,
                        LocalDate workEndDate, int rating, Long totalPrice, int floor,
                        String companyName,
                        String memberNickName, List<ReviewImageResponse> reviewImageResponses,
                        List<ReviewConstructionTypeResponse> reviewConstructionTypeResponses,
                        String buildingTypeName) {

        this.reviewId = reviewId;
        this.content = content;
        this.title = title;
        this.regDate = regDate;
        this.workStartDate = workStartDate;
        this.workEndDate = workEndDate;
        this.rating = rating;
        this.totalPrice = totalPrice;
        this.floor = floor;
        this.companyName = companyName;
        this.memberNickName = memberNickName;
        this.reviewImageResponses = reviewImageResponses;
        this.reviewConstructionTypeResponses = reviewConstructionTypeResponses;
        this.buildingTypeName = buildingTypeName;
    }
}
