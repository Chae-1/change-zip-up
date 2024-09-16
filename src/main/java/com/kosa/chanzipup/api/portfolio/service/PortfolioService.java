package com.kosa.chanzipup.api.portfolio.service;

import com.kosa.chanzipup.api.portfolio.controller.request.PortfolioRegisterRequest;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioDetailResponse;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioListResponse;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioRegisterResponse;

import com.kosa.chanzipup.domain.account.Account;

import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.portfolio.PortfolioRepository;
import com.kosa.chanzipup.domain.portfolio.PortfolioConstructionType;
import com.kosa.chanzipup.domain.portfolio.PortfolioConstructionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final PortfolioConstructionTypeRepository portfolioConstructionTypeRepository;
    private final BuildingTypeRepository buildingTypeRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public PortfolioRegisterResponse registerPortfolio(PortfolioRegisterRequest request, Account account) throws IOException {

        // 빌딩 타입 조회
        BuildingType buildingType = buildingTypeRepository.findById(request.getBuildingTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid building type ID: " + request.getBuildingTypeId()));

        // Portfolio 저장
        Portfolio portfolio = Portfolio.ofNewPortfolio(
                request.getTitle(),
                request.getContent(),
                request.getProjectArea(),
                request.getProjectBudget(),
                request.getProjectLocation(),
                request.getStartDate(),
                request.getEndDate(),
                account,
                buildingType
        );

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // 시공 서비스 저장
        for (Long constructionTypeId : request.getConstructionService()) {
            ConstructionType constructionType = constructionTypeRepository.findById(constructionTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid construction type ID: " + constructionTypeId));

            PortfolioConstructionType portfolioConstructionType = new PortfolioConstructionType();
            portfolioConstructionType.setPortfolio(savedPortfolio);
            portfolioConstructionType.setConstructionType(constructionType);
            portfolioConstructionTypeRepository.save(portfolioConstructionType);
        }

        return PortfolioRegisterResponse.of(savedPortfolio.getId());
    }

    // 시공사례 리스트 조회
    public List<PortfolioListResponse> getAllPortfolios() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        List<PortfolioListResponse> portfolioResponses = new ArrayList<>();

        for (Portfolio portfolio : portfolios) {
            // BuildingType이 null인 경우 처리
            BuildingType buildingType = portfolio.getBuildingType();
            String buildingTypeName = (buildingType != null) ? buildingType.getName() : "Unknown Building Type";  // null 체크

            PortfolioListResponse portfolioResponse = new PortfolioListResponse(
                    portfolio.getId(),
                    portfolio.getTitle(),
                    portfolio.getProjectArea(),
                    portfolio.getProjectLocation(),
                    buildingTypeName
            );

            portfolioResponses.add(portfolioResponse);
        }

        return portfolioResponses;
    }

    // 시공사례 상세 조회
    public PortfolioDetailResponse getPortfolioById(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid portfolio ID: " + id));

        // 시공 서비스 이름들 가져오기
        List<String> services = new ArrayList<>();
        for (PortfolioConstructionType constructionType : portfolio.getConstructionTypes()) {
            services.add(constructionType.getConstructionType().getName());
        }

        // 빌딩 타입 이름 가져오기
        String buildingTypeName = (portfolio.getBuildingType() != null) ? portfolio.getBuildingType().getName() : "Unknown Building Type";

        // PortfolioDetailResponse 생성 및 반환
        return new PortfolioDetailResponse(
                portfolio.getId(),
                portfolio.getTitle(),
                portfolio.getContent(),
                portfolio.getProjectArea(),
                portfolio.getProjectBudget(),
                portfolio.getProjectLocation(),
                portfolio.getStartDate(),
                portfolio.getEndDate(),
                buildingTypeName,
                services
        );
    }
}
