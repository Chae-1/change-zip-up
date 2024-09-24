package com.kosa.chanzipup.api.estimate.controller.response;

import com.kosa.chanzipup.domain.estimate.Estimate;
import lombok.Getter;

import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Getter
public class EstimateUpdateResponse {

    private Map<Long, EstimatePriceResponse> constructionPrices;
    private int totalPrice;

    public EstimateUpdateResponse(Estimate estimate) {
        this.constructionPrices = estimate
                .getEstimatePrices()
                .stream()
                .collect(toMap(price -> price.getId(), price -> new EstimatePriceResponse(price.getConstructionType().getTypeName(), price.getPrice())));

        this.totalPrice = estimate.getTotalPrices();
    }

    static class EstimatePriceResponse {
        private String typeName;
        private int price;

        public EstimatePriceResponse(String typeName, int price) {
            this.typeName = typeName;
            this.price = price;
        }
    }
}
