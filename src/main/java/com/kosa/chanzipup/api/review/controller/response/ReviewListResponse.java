package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewListResponse {
    // 리뷰 목록 조회하기 위해서 클라이언트에게 꼭 필요한 정보만 전달하기 위해서
    private Long id;

    private String title;

    private String content;

    private LocalDateTime regDate;  // 게시일

    private LocalDateTime workStartDateTime;

    private LocalDateTime workEndDateTime;

    private double rating;  // 별점

    private MemberForReviewResponse member;

    private CompanyForReviewResponse company;

    private List<ReviewImagesForReviewResponse> reviewImages;

    private BuildingTypeForReivewResponse buildingType;

    private ConstructionTypeForReivewResponse constructionType;

    private Long totalPrice;

    private int floor;

//    private EstimateRequestForReviewResponse estimate;  // 견적 요청서
//    private EstimateForReviewResponse estimate;    // 견적서
}
