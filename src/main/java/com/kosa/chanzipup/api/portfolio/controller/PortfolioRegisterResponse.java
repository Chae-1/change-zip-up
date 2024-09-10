package com.kosa.chanzipup.api.portfolio.controller;

import com.kosa.chanzipup.domain.portfolio.Portfolio;
import lombok.Getter;

@Getter
public class PortfolioRegisterResponse {
    private String id;

    private PortfolioRegisterResponse(String id) {
        this.id = id;
    }

    public static PortfolioRegisterResponse of(String id) {
        return new PortfolioRegisterResponse(id);
    }
}
