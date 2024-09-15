package com.kosa.chanzipup.api.review.controller.request;

import com.kosa.chanzipup.domain.review.ReviewImages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewRegisterRequest {

    @NotBlank(message = "제목은 반드시 입력되어야 합니다.")
    private String title;

    @NotBlank(message = "내용은 반드시 입력되어야 합니다.")
    private String content;

    @NotNull(message = "게시일은 반드시 입력되어야 합니다.")
    private LocalDateTime regDate;

    @NotNull(message = "시작 날짜는 반드시 입력되어야 합니다.")
    private LocalDateTime workStartDateTime;

    @NotNull(message = "종료 날짜는 반드시 입력되어야 합니다.")
    private LocalDateTime workEndDateTime;

    @NotNull(message = "별점은 반드시 입력되어야 합니다.")
    private double rating;

    private List<ReviewImages> reviewImages;

    // 추후에 자동 입력방식으로 하면 아래 조건들은 다 지워도 됨

    @NotBlank(message = "건물 종류는 반드시 입력되어야 합니다.")
    private Long buildingTypeId;

    @NotBlank(message = "시공 종류는 반드시 입력되어야 합니다.")
    private List<Long> constructionService;

    @NotNull(message = "시공 금액은 반드시 입력되어야 합니다.")
    private Long totalPrice;

    @NotNull(message = "평수는 반드시 입력되어야 합니다.")
    private int floor;
}