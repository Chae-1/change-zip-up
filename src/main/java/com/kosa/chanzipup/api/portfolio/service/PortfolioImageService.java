package com.kosa.chanzipup.api.portfolio.service;

import com.kosa.chanzipup.domain.portfolio.PortFolioImageRepository;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.portfolio.PortfolioImage;
import com.kosa.chanzipup.domain.portfolio.PortfolioRepository;
import com.kosa.chanzipup.domain.review.Review;
import com.kosa.chanzipup.domain.review.ReviewImages;
import com.kosa.chanzipup.domain.review.ReviewImagesRepository;
import com.kosa.chanzipup.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PortfolioImageService {

    private final PortfolioRepository portfolioRepository;

    private final PortFolioImageRepository portFolioImageRepository;

    private final String domainAddress;

    public PortfolioImageService(PortfolioRepository portfolioRepository, PortFolioImageRepository portFolioImageRepository,
                                 @Value("${domain.address}") String domainAddress) {
        this.portfolioRepository = portfolioRepository;
        this.portFolioImageRepository = portFolioImageRepository;
        this.domainAddress = domainAddress;
    }

    public String addPortfolioImage(Long portfolioId, String uploadEndPoint) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException());

        PortfolioImage image = portFolioImageRepository.save(PortfolioImage.of(portfolio, String.format("%s%s", domainAddress, uploadEndPoint)));
        return image.getImageUrl();

    }
}
