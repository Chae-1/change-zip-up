package com.kosa.chanzipup.api.portfolio.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PortfolioUpdateRequest {
    @NotBlank(message = "제목은 반드시 입력되어야 합니다.")
    private String title;

    @NotBlank(message = "내용은 반드시 입력되어야 합니다.")
    private String content;

    private int projectArea;

    @NotNull(message = "예산은 반드시 입력되어야 합니다.")
    private int projectBudget;

    private String projectLocation;

    @NotNull(message = "시작 날짜는 반드시 입력되어야 합니다.")
    private LocalDate startDate;

    @NotNull(message = "종료 날짜는 반드시 입력되어야 합니다.")
    private LocalDate endDate;

    @NotNull(message = "시공 종류는 반드시 선택되어야 합니다.")
    private List<Long> constructionService;

    @NotNull(message = "건물 종류는 반드시 선택되어야 합니다.")
    private Long buildingTypeId;

}
