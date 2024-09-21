package com.kosa.chanzipup.api.estimate.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EstimatePriceRequest {

    Map<Long, Integer> constructionPrices;
}
