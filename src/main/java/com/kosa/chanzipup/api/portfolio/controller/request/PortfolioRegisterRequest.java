package com.kosa.chanzipup.api.portfolio.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PortfolioRegisterRequest {
    @NotBlank(message = "제목은 반드시 입력되어야 합니다.")
    private String title;

    @NotBlank(message = "내용은 반드시 입력되어야 합니다.")
    private String content;

    private int projectArea;

    @NotBlank(message = "예산은 반드시 입력되어야 합니다.")
    private int projectBudget;

    private String projectLocation;

    @NotBlank(message = "시작 날짜는 반드시 입력되어야 합니다.")
    private LocalDate startDate;

    @NotBlank(message = "종료 날짜는 반드시 입력되어야 합니다.")
    private LocalDate endDate;

    @NotBlank(message = "시공 종류는 반드시 선택되어야 합니다.")
    private List<Long> constructionService;

}
