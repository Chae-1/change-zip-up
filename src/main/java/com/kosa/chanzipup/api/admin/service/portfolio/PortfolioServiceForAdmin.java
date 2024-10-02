package com.kosa.chanzipup.api.admin.service.portfolio;

import com.kosa.chanzipup.api.admin.controller.response.portfolio.PortfolioDetailResponse;
import com.kosa.chanzipup.api.admin.controller.response.portfolio.PortfolioListResponse;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.portfolio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kosa.chanzipup.application.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioServiceForAdmin {

    private final PortfolioRepository portfolioRepository;

    private final PortfolioConstructionTypeRepository portfolioConstructionTypeRepository;

    private final CompanyRepository companyRepository;

    private final PortFolioImageRepository portFolioImageRepository;

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

    // 상세 조회
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

        // Account에서 회사 정보 가져오기 (Account가 Company인 경우)
        Company company = portfolio.getCompany();
        Long companyId = company.getId();
        String companyPhone = company.getPhoneNumber();

        // Company가 존재하는지 확인하여 가져오기
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isPresent()) {
            Company findCompany = optionalCompany.get();
            String companyName = findCompany.getCompanyName();
            String companyAddress = findCompany.getAddress();
            String companyLogo = findCompany.getCompanyLogoUrl();

            return new PortfolioDetailResponse(
                    portfolio.getId(),
                    portfolio.getTitle(),
                    portfolio.getContent(),
                    portfolio.getFloor(),
                    portfolio.getProjectBudget(),
                    portfolio.getProjectLocation(),
                    portfolio.getStartDate(),
                    portfolio.getEndDate(),
                    buildingTypeName,
                    services,
                    companyId,
                    companyName,
                    companyAddress,
                    companyPhone,
                    companyLogo,
                    portfolio.getCreatedAt(),
                    portfolio.getUpdatedAt()
            );
        } else {
            // Company가 아니면 일반 Account 정보를 반환
            return new PortfolioDetailResponse(
                    portfolio.getId(),
                    portfolio.getTitle(),
                    portfolio.getContent(),
                    portfolio.getFloor(),
                    portfolio.getProjectBudget(),
                    portfolio.getProjectLocation(),
                    portfolio.getStartDate(),
                    portfolio.getEndDate(),
                    buildingTypeName,
                    services,
                    companyId,
                    company.getName(),
                    "No Address",
                    companyPhone,
                    "",
                    portfolio.getCreatedAt(),
                    portfolio.getUpdatedAt()
            );
        }
    }

    // 삭제
    @Transactional
    public List<String> deletePortfolio(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("portfolio 수정 불가"));

        List<PortfolioImage> portfolioImages = portFolioImageRepository
                .findAllByPortFolioId(portfolio.getId());

        List<String> deletePortfolioUrls = portfolioImages.stream()
                .map(PortfolioImage::getImageUrl)
                .toList();

        portfolioConstructionTypeRepository.deleteAllByPortfolioId(portfolio.getId());
        portfolioRepository.deleteById(id);
        portFolioImageRepository.deleteAllByPortfolioId(portfolio.getId());

        return deletePortfolioUrls;
    }
}
