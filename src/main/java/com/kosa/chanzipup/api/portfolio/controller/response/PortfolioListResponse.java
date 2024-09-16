package com.kosa.chanzipup.api.portfolio.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PortfolioListResponse {

    private Long id;

    private String title;

    private int projectArea; // 평수

    private String projectLocation;

    private String buildingType;
}