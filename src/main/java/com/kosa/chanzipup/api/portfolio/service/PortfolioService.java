package com.kosa.chanzipup.api.portfolio.service;

import com.kosa.chanzipup.api.portfolio.controller.request.PortfolioRegisterRequest;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioRegisterResponse;

import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRepository;

import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.portfolio.PortFolioImageRepository;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.portfolio.PortfolioRepository;
import com.kosa.chanzipup.domain.portfolio.PortfolioConstructionType;
import com.kosa.chanzipup.domain.portfolio.PortfolioConstructionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortFolioImageRepository portfolioImageRepository;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final PortfolioConstructionTypeRepository portfolioConstructionTypeRepository;
    private final AccountRepository accountRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public PortfolioRegisterResponse registerPortfolio(PortfolioRegisterRequest request, Account account) throws IOException {

        // Portfolio 저장
        Portfolio portfolio = Portfolio.ofNewPortfolio(
                request.getTitle(),
                request.getContent(),
                request.getProjectArea(),
                request.getProjectBudget(),
                request.getProjectLocation(),
                request.getStartDate(),
                request.getEndDate(),
                account
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

//        // 이미지 저장
//        for (MultipartFile image : images) {
//            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
//            Path imagePath = Paths.get(uploadDir, fileName);
//            Files.write(imagePath, image.getBytes());
//
//            PortfolioImage portfolioImage = new PortfolioImage();
//            portfolioImage.setImageUrl("/images/" + fileName);
//            portfolioImage.setPortfolio(savedPortfolio);
//            portfolioImageRepository.save(portfolioImage);
//        }

        return PortfolioRegisterResponse.of(savedPortfolio.getId());
    }
}
