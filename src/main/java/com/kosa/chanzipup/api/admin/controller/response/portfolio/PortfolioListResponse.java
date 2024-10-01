package com.kosa.chanzipup.api.admin.controller.response.portfolio;

import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.portfolio.PortfolioConstructionType;
import com.kosa.chanzipup.domain.portfolio.PortfolioImage;
import lombok.Getter;

import java.util.List;

@Getter
public class PortfolioListResponse {
    private Long id;

    private String title;

    private String companyName;

    private int floor;

    private String projectLocation;

    private String buildingType;

    private List<String> imageUrls;

    private List<String> constructionTypes;

    public PortfolioListResponse(Portfolio portfolio, List<PortfolioConstructionType> types) {
        this.id = portfolio.getId();
        this.title = portfolio.getTitle();
        this.companyName = portfolio.getCompany().getCompanyName();
        this.floor = portfolio.getFloor();
        this.projectLocation = portfolio.getProjectLocation();
        this.buildingType = portfolio.getBuildingType().getName();
        this.imageUrls = portfolio.getPortfolioImages()
                .stream()
                .map(PortfolioImage::getImageUrl)
                .toList();
        this.constructionTypes = types.stream()
                .map(type -> type.getConstructionType().getName())
                .toList();
    }
}
