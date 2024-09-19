package com.kosa.chanzipup.api.estimate.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstimateConstructionResponse {
    private Long estimateConstructionTypeId;
    private String constructionTypeName;

    public EstimateConstructionResponse(Long estimateConstructionTypeId, String constructionTypeName) {
        this.estimateConstructionTypeId = estimateConstructionTypeId;
        this.constructionTypeName = constructionTypeName;
    }
}
