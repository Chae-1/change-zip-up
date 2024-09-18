package com.kosa.chanzipup.api.review.service;

import com.kosa.chanzipup.domain.review.Review;
import com.kosa.chanzipup.domain.review.ReviewImages;
import com.kosa.chanzipup.domain.review.ReviewImagesRepository;
import com.kosa.chanzipup.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ReviewImagesService {

    private final ReviewRepository reviewRepository;

    private final ReviewImagesRepository reviewImagesRepository;

    private final String domainAddress;

    public ReviewImagesService(ReviewRepository reviewRepository, ReviewImagesRepository reviewImagesRepository,
                               @Value("${domain.address}") String domainAddress) {
        this.reviewRepository = reviewRepository;
        this.reviewImagesRepository = reviewImagesRepository;
        this.domainAddress = domainAddress;
    }

    @Transactional
    public String addReviewImage(Long reviewId, String uploadEndPoint) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException());

        ReviewImages image = reviewImagesRepository.save(ReviewImages.of(review, String.format("%s%s", domainAddress ,uploadEndPoint)));
        return image.getImageUrl();
    }
}
