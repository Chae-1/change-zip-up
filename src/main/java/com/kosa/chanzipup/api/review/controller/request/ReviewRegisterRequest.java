package com.kosa.chanzipup.api.review.controller.request;

import com.kosa.chanzipup.domain.review.ReviewImages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewRegisterRequest {

    @NotBlank(message = "제목은 반드시 입력되어야 합니다.")
    private String title;

    @NotNull(message = "시작 날짜는 반드시 입력되어야 합니다.")
    private LocalDate workStartDate;

    @NotNull(message = "종료 날짜는 반드시 입력되어야 합니다.")
    private LocalDate workEndDate;

    private int rating;

    private String companyName;

//    private List<ReviewImages> reviewImages;
//
//    @NotBlank(message = "건물 종류는 반드시 입력되어야 합니다.")
//    private Long buildingTypeId;
//
//    @NotBlank(message = "시공 종류는 반드시 입력되어야 합니다.")
//    private List<Long> constructionService;

    @NotNull(message = "시공 금액은 반드시 입력되어야 합니다.")
    private Long totalPrice;

    @NotNull(message = "평수는 반드시 입력되어야 합니다.")
    private int floor;
}