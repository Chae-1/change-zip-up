package com.kosa.chanzipup.api.review.controller.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class ReviewDetail {
    // 후기 내용
    private Long reviewId;
    private String content;
    private String title;
    private LocalDateTime regDate;
    private LocalDate workStartDate;
    private LocalDate workEndDate;
    private int rating;
    private Long totalPrice;
    private int floor;

    // 회사 내용
    private Long companyId;
    private String companyName;

    // 고객 정보
    private String memberNickName;
    private Long memberId;

    // 작성자 여부
    private boolean isUpdatable;

    private List<ReviewImageResponse> reviewImageResponses;
    private List<ReviewConstructionTypeResponse> reviewConstructionTypeResponses;

    public ReviewDetail(Long reviewId, String content, String title, LocalDateTime regDate, LocalDate workStartDate,
                        LocalDate workEndDate, int rating, Long totalPrice, int floor, Long companyId,
                        String companyName,
                        String memberNickName, Long memberId, boolean isUpdatable, List<ReviewImageResponse> reviewImageResponses,
                        List<ReviewConstructionTypeResponse> reviewConstructionTypeResponses) {
        this.reviewId = reviewId;
        this.content = content;
        this.title = title;
        this.regDate = regDate;
        this.workStartDate = workStartDate;
        this.workEndDate = workEndDate;
        this.rating = rating;
        this.totalPrice = totalPrice;
        this.floor = floor;
        this.companyId = companyId;
        this.companyName = companyName;
        this.memberNickName = memberNickName;
        this.memberId = memberId;
        this.isUpdatable = isUpdatable;
        this.reviewImageResponses = reviewImageResponses;
        this.reviewConstructionTypeResponses = reviewConstructionTypeResponses;
    }




}
