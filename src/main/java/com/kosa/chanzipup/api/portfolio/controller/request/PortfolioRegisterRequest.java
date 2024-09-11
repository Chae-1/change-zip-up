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

    private int projectBudget;

    private String projectLocation;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<Long> constructionService;

}
