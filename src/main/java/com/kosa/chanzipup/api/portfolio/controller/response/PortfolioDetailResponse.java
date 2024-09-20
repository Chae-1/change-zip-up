package com.kosa.chanzipup.api.portfolio.controller.response;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class PortfolioDetailResponse {

    private Long id;

    private String title;

    @Lob
    private String content;

    private int projectArea;

    private int projectBudget;

    private String projectLocation;

    private LocalDate startDate;

    private LocalDate endDate;

    private String buildingType;

    private List<String> services;

    private Long companyId;

    private String companyName;

    private String companyAddress;

    private String companyPhone;

    private String companyLogo;


}
