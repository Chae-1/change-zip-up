package com.kosa.chanzipup.api.portfolio.service;

import com.kosa.chanzipup.application.PathMatchService;
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
@RequiredArgsConstructor
@Slf4j
public class PortfolioImageService {

    private final PortfolioRepository portfolioRepository;

    private final PortFolioImageRepository portFolioImageRepository;

    private final PathMatchService pathMatchService;


    public String addPortfolioImage(Long portfolioId, String uploadEndPoint) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException());

        PortfolioImage image = portFolioImageRepository.save(PortfolioImage.of(portfolio, pathMatchService.match(uploadEndPoint)));
        return image.getImageUrl();

    }
}
