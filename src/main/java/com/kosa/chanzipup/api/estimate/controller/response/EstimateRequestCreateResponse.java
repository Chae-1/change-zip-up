package com.kosa.chanzipup.api.estimate.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstimateRequestCreateResponse {

    private List<Long> constructionTypeIds;
    private String city;
    private String district;

    public EstimateRequestCreateResponse(List<Long> constructionTypeIds, String address) {
        this.constructionTypeIds = constructionTypeIds;
        String[] fullAddress = address.split(" ");
        this.city = fullAddress[0];
        this.district = fullAddress[1];
    }
}
