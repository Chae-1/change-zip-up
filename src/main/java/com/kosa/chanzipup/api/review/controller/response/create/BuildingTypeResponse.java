package com.kosa.chanzipup.api.review.controller.response.create;

import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import lombok.Getter;

@Getter
public class BuildingTypeResponse {
    private String buildingTypeName;
    private Long buildingTypeId;

    public BuildingTypeResponse(BuildingType buildingType) {
        this.buildingTypeId = buildingType.getId();
        this.buildingTypeName = buildingType.getName();
    }
}
