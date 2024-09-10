package com.kosa.chanzipup.api.portfolio.controller;

import com.kosa.chanzipup.api.portfolio.controller.request.PortfolioRegisterRequest;
import com.kosa.chanzipup.api.portfolio.service.PortfolioService;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Transactional
    @PostMapping
    public ResponseEntity<PortfolioRegisterResponse> registerPortfolio(@RequestBody PortfolioRegisterRequest registerRequest) {
        PortfolioRegisterResponse savedPortfolio = portfolioService.registerPortfolio(registerRequest);
        return ResponseEntity.ok(savedPortfolio);
    }

}
