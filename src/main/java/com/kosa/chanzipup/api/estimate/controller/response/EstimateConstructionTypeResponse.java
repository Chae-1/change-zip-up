package com.kosa.chanzipup.api.estimate.controller.response;

import lombok.Getter;

@Getter
public class EstimateConstructionTypeResponse {
    private String name;

    public EstimateConstructionTypeResponse(String name) {
        this.name = name;
    }
}
