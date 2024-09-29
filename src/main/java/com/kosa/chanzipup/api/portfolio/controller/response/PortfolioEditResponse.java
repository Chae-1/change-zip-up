package com.kosa.chanzipup.api.portfolio.controller.response;

import com.kosa.chanzipup.api.review.controller.response.create.ConstructionTypeResponse;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.portfolio.PortfolioConstructionType;
import com.kosa.chanzipup.domain.portfolio.PortfolioImage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PortfolioEditResponse {

    private String buildingTypeName;
    private List<String> savedImageUrls;
    private String content;
    private long id;
    private int floor;
    private int budget;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<ConstructionTypeResponse> selectedTypes;
    private List<ConstructionTypeResponse> totalTypes;

    public PortfolioEditResponse(Portfolio portfolio,
                                 List<PortfolioConstructionType> portfolioTypes,
                                 List<ConstructionType> constructionTypes) {
        this.floor = portfolio.getFloor();
        this.content = portfolio.getContent();
        this.savedImageUrls =  portfolio.getPortfolioImages()
                .stream()
                .map(PortfolioImage::getImageUrl)
                .toList();
        this.id = portfolio.getId();
        this.budget = portfolio.getProjectBudget();
        this.title = portfolio.getTitle();
        this.startDate = portfolio.getStartDate();
        this.endDate = portfolio.getEndDate();


        this.selectedTypes = portfolioTypes.stream()
                .map(PortfolioConstructionType::getConstructionType)
                .map(ConstructionTypeResponse::new)
                .toList();

        this.totalTypes = constructionTypes
                .stream()
                .map(ConstructionTypeResponse::new)
                .toList();
    }
}
