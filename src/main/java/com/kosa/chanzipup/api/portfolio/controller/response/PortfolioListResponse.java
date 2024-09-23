package com.kosa.chanzipup.api.portfolio.controller.response;

import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.portfolio.PortfolioImage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor

public class PortfolioListResponse {

    private Long id;

    private String title;

    private int floor;

    private String projectLocation;

    private String buildingType;

    private List<String> imageUrls;

    public PortfolioListResponse(Portfolio portfolio) {
        this.id = portfolio.getId();
        this.title = portfolio.getTitle();
        this.floor = portfolio.getFloor();
        this.projectLocation = portfolio.getProjectLocation();
        this.buildingType = portfolio.getBuildingType().getName();
        this.imageUrls = portfolio.getPortfolioImages()
                .stream()
                .map(PortfolioImage::getImageUrl)
                .toList();
    }
}