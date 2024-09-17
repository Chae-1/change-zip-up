package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import lombok.Getter;

@Getter
public class ConstructionTypeForReivewResponse {

    private String constructionType;

    public ConstructionTypeForReivewResponse(ConstructionType constructionType) {
        this.constructionType = constructionType.getName();
    }
}
