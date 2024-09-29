package com.kosa.chanzipup.api.portfolio.service;

import com.kosa.chanzipup.api.portfolio.controller.request.PortfolioRegisterRequest;
import com.kosa.chanzipup.api.portfolio.controller.request.PortfolioUpdateRequest;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioDetailResponse;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioEditResponse;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioListResponse;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioRegisterResponse;

import com.kosa.chanzipup.application.Page;

import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.portfolio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final PortfolioConstructionTypeRepository portfolioConstructionTypeRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final CompanyRepository companyRepository;
    private final PortfolioQueryRepository queryRepository;
    private final PortFolioImageRepository portFolioImageRepository;


    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public PortfolioRegisterResponse registerPortfolio(PortfolioRegisterRequest request,
                                                       String email) throws IOException {

        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email: " + email));

        // 빌딩 타입 조회
        BuildingType buildingType = buildingTypeRepository.findById(request.getBuildingTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid building type ID: " + request.getBuildingTypeId()));

        // Portfolio 저장
        Portfolio portfolio = Portfolio.ofNewPortfolio(
                request.getTitle(),
                request.getContent(),
                request.getFloor(),
                request.getProjectBudget(),
                request.getProjectLocation(),
                request.getStartDate(),
                request.getEndDate(),
                company,
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
        List<Portfolio> portfolios = portfolioRepository.findAllWithImages();

        List<Long> portfolioIds = portfolios.stream()
                .map(Portfolio::getId)
                .toList();

        Map<Long, List<PortfolioConstructionType>> types = portfolioConstructionTypeRepository
                .findByPortfolioIdIn(portfolioIds)
                .stream()
                .collect(Collectors.groupingBy(type -> type.getPortfolio().getId(), toList()));

        return portfolios
                .stream()
                .map(portfolio -> new PortfolioListResponse(portfolio, types.get(portfolio.getId())))
                .toList();
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

    @Transactional
    public String updatePortfolio(Long portfolioId, PortfolioUpdateRequest portfolioRequest) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시공 사례입니다."));

        portfolio.update(portfolioRequest);

        return portfolio.getContent();
    }

    public Page<List<PortfolioListResponse>> getAllPortfoliosWithPage(int pageNumber, int pageSize) {
        return Page.of(getAllPortfolios(), pageSize, pageNumber);
    }

    public PortfolioEditResponse getPortfolioDetailForUpdate(String email, Long portfolioId) {
        Portfolio portfolio = queryRepository.findPortfolioWithAll(portfolioId, email)
                .orElseThrow(() -> new IllegalArgumentException("수정 할 수 없습니다."));
        List<PortfolioConstructionType> portfolioTypes = portfolioConstructionTypeRepository
                .findByPortfolioId(portfolio.getId());
        List<ConstructionType> constructionTypes = constructionTypeRepository.findAll();

        return new PortfolioEditResponse(portfolio, portfolioTypes, constructionTypes);
    }

    @Transactional
    public List<String> deletePortfolio(Long portfolioId, String email) {
        Portfolio portfolio = portfolioRepository.findByIdAndCompanyEmail(portfolioId, email)
                .orElseThrow(() -> new IllegalArgumentException("portfolio 수정 불가"));

        List<PortfolioImage> portfolioImages = portFolioImageRepository
                .findAllByPortFolioId(portfolio.getId());

        List<String> deletePortfolioUrls = portfolioImages.stream()
                .map(PortfolioImage::getImageUrl)
                .toList();

        portfolioConstructionTypeRepository.deleteAllByPortfolioId(portfolio.getId());
        portfolioRepository.deleteById(portfolioId);
        portFolioImageRepository.deleteAllByPortfolioId(portfolio.getId());

        return deletePortfolioUrls;
    }
}
