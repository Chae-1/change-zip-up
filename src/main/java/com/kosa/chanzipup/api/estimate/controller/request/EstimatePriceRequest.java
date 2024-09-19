package com.kosa.chanzipup.api.estimate.controller.request;

import lombok.Getter;

import java.util.Map;

@Getter
public class EstimatePriceRequest {
    Map<Long, Integer> constructionPrices;
}
