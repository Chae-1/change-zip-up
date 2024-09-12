package com.kosa.chanzipup.api.portfolio.controller.response;

import lombok.Getter;

@Getter
public class PortfolioRegisterResponse {
    private final Long id;

    private PortfolioRegisterResponse(Long id) {
        this.id = id;
    }

    public static PortfolioRegisterResponse of(Long id) {
        return new PortfolioRegisterResponse(id);
    }
}
