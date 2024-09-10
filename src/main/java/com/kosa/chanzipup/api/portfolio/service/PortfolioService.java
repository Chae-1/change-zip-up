package com.kosa.chanzipup.api.portfolio.service;

import com.kosa.chanzipup.api.portfolio.controller.PortfolioRegisterResponse;
import com.kosa.chanzipup.api.portfolio.controller.request.PortfolioRegisterRequest;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public PortfolioRegisterResponse registerPortfolio(PortfolioRegisterRequest request) {
        Portfolio portfolio = Portfolio.ofNewPortfolio(request.getTitle(), request.getContent(), request.getProjectType(),
                request.getProjectArea(), request.getProjectBudget(), request.getProjectLocation(), request.getStartDate(),
                request.getEndDate());

        Portfolio savedportfolio = portfolioRepository.save(portfolio);

        return PortfolioRegisterResponse.of(String.valueOf(savedportfolio.getId()));
    }

}
