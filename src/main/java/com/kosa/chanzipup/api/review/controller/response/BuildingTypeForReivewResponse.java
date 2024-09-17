package com.kosa.chanzipup.api.review.controller.response;

import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import lombok.Getter;

@Getter
public class BuildingTypeForReivewResponse {

    private String buildingType;

    public BuildingTypeForReivewResponse(BuildingType buildingType) {
        this.buildingType = buildingType.getName();
    }
}
