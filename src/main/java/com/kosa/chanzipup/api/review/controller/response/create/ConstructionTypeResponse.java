package com.kosa.chanzipup.api.review.controller.response.create;

import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import lombok.Getter;

@Getter
public class ConstructionTypeResponse {
    private Long constructionId;
    private String constructionName;

    public ConstructionTypeResponse(ConstructionType constructionType) {
        this.constructionId = constructionType.getId();
        this.constructionName = constructionType.getName();
    }
}
