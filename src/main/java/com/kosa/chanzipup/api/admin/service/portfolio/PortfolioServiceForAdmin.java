package com.kosa.chanzipup.api.admin.service.portfolio;

import com.kosa.chanzipup.api.admin.controller.response.portfolio.PortfolioListResponse;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.portfolio.PortfolioConstructionType;
import com.kosa.chanzipup.domain.portfolio.PortfolioConstructionTypeRepository;
import com.kosa.chanzipup.domain.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kosa.chanzipup.application.Page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioServiceForAdmin {

    private final PortfolioRepository portfolioRepository;

    private final PortfolioConstructionTypeRepository portfolioConstructionTypeRepository;

    // 목록 조회
    public Page<List<PortfolioListResponse>> getAllPortfolios(Pageable pageable) {
        List<Portfolio> portfolios = portfolioRepository.findAllWithImages();

        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();

        List<Long> portfolioIds = portfolios.stream()
                .map(Portfolio::getId)
                .toList();

        Map<Long, List<PortfolioConstructionType>> types = portfolioConstructionTypeRepository
                .findByPortfolioIdIn(portfolioIds)
                .stream()
                .collect(Collectors.groupingBy(type -> type.getPortfolio().getId(), toList()));

        List<PortfolioListResponse> portfolioListResponses = portfolios
                .stream()
                .map(portfolio -> new PortfolioListResponse(portfolio, types.get(portfolio.getId())))
                .toList();

        return Page.of(portfolioListResponses, pageSize, pageNumber);
    }
}
