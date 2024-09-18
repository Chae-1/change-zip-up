package com.kosa.chanzipup.api.review.controller.response.create;

import java.util.List;
import lombok.Getter;

@Getter
public class ReviewCreationPage {
    private List<BuildingTypeResponse> buildingTypeResponses;
    private List<ConstructionTypeResponse> constructionTypeResponses;

    public ReviewCreationPage(List<BuildingTypeResponse> buildingTypeResponses,
                              List<ConstructionTypeResponse> constructionTypes) {
        this.buildingTypeResponses = buildingTypeResponses;
        this.constructionTypeResponses = constructionTypes;
    }
}
