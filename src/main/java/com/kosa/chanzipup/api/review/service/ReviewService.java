package com.kosa.chanzipup.api.review.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import com.kosa.chanzipup.api.review.controller.request.ReviewRegisterRequest;
import com.kosa.chanzipup.api.review.controller.response.*;
import com.kosa.chanzipup.application.Page;
import com.kosa.chanzipup.application.images.ImageService;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.review.*;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final ReviewConstructionTypeRepository reviewConstructionTypeRepository;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final CompanyRepository companyRepository;
    private final ReviewImagesRepository reviewImagesRepository;
    private final ImageService imageService;

    @Transactional
    public ReviewRegisterResponse registerReview(ReviewRegisterRequest request, String email) {
        // 1. 작성자 조회, company 조회, 이후 review 초기 정보 저장.
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Company company = companyRepository.findByCompanyName(request.getCompanyName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파트너입니다."));

        // 시공 건물 종류.
        BuildingType buildingType = buildingTypeRepository.findById(request.getBuildingTypeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 건물 종류입니다."));

        // 시공 서비스 타입
        List<ConstructionType> constructionTypes = constructionTypeRepository.findByIdIn(request.getConstructionTypes());

        Review review = Review.ofNewReview(request.getTitle(), LocalDateTime.now(), request.getWorkStartDate(),
                request.getWorkEndDate(), request.getRating(), member, company,
                buildingType, constructionTypes, request.getTotalPrice(), request.getFloor());

        reviewRepository.save(review);

        updateCompanyRating(company);

        return new ReviewRegisterResponse(review.getId());
    }

    private void updateCompanyRating(Company company) {
        double companyRating = reviewRepository.findByCompanyId(company.getId())
                        .stream()
                        .mapToDouble(Review::getRating)
                        .average()
                        .orElse(0.0); // 만약 평점이 없을 경우 0.0을 반환하도록 처리

        // 소수점 첫째 자리까지 반올림
        companyRating = Math.round(companyRating * 10) / 10.0;
        company.updateRating(companyRating);
    }

    public List<ReviewResponse> getAllReviews() {

        List<Review> reviews = reviewRepository.findAllWithAll();

        Map<Long, List<ReviewImages>> reviewImageMap = getReviewImageMap(reviews);

        Map<Long, List<ReviewConstructionType>> reviewConstructionTypes = getConstructionTypes(reviews);

        return reviews
                .stream()
                .map((review) -> new ReviewResponse(review, review.getCompany(),
                        review.getMember(), reviewImageMap.get(review.getId()),
                        reviewConstructionTypes.get(review.getId()), review.getBuildingType()))
                .toList();
    }

    public Page<List<ReviewResponse>> getAllReviewsWithPage(int pageSize, int pageNumber) {

        List<Review> reviews = reviewRepository.findAllWithAll();

        Map<Long, List<ReviewImages>> reviewImageMap = getReviewImageMap(reviews);

        Map<Long, List<ReviewConstructionType>> reviewConstructionTypes = getConstructionTypes(reviews);

        List<ReviewResponse> reviewResponses = reviews
                .stream()
                .map((review) -> new ReviewResponse(review, review.getCompany(), review.getMember(),
                        reviewImageMap.get(review.getId()), reviewConstructionTypes.get(review.getId()), review.getBuildingType()))
                .toList();

        return Page.of(reviewResponses, pageSize, pageNumber);
    }


    private Map<Long, List<ReviewImages>> getReviewImageMap(List<Review> reviews) {
        List<Long> reviewIds = getReviewIds(reviews);

        List<ReviewImages> reviewImages = reviewImagesRepository.findAllByReviewIds(reviewIds);

        return reviewImages.stream()
                .collect(groupingBy((reviewImage) -> reviewImage.getReview().getId(), toList()));
    }

    private List<Long> getReviewIds(List<Review> reviews) {
        List<Long> reviewIds = reviews.stream()
                .map(Review::getId)
                .toList();
        return reviewIds;
    }

    @Transactional
    public void updateReviewContent(Long reviewId, String content) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException());

        review.updateContent(content);
    }

    private Map<Long, List<ReviewConstructionType>> getConstructionTypes(List<Review> reviews) {
        List<Long> reviewIds = getReviewIds(reviews);

        List<ReviewConstructionType> reviewConstructionTypes = reviewConstructionTypeRepository.findAllByReviewIds(reviewIds);

        return reviewConstructionTypes.stream()
                .collect(groupingBy((type) -> type.getReview().getId(), toList()));
    }

    @Transactional
    public List<String> deleteReview(Long reviewId, String userEmail) {
        Review review = reviewRepository.findByIdAndUserEmail(reviewId, userEmail)
                .orElseThrow(() -> new IllegalArgumentException("삭제 할 수 없습니다."));

        List<String> imageUrls = review.getReviewImages()
                .stream()
                .map(ReviewImages::getImageUrl)
                .toList();

        // 연관 객체 삭제
        reviewImagesRepository.deleteByReviewId(review.getId());
        reviewConstructionTypeRepository.deleteByReviewId(review.getId());
        reviewRepository.deleteById(reviewId);

        // 실제 이미지 삭제
        imageService.deleteAllImages(imageUrls);

        return imageUrls;
    }

    public List<ReviewResponse> getAllMemberWriteReviews(String email) {
        List<Review> reviews = reviewRepository.findAllByMemberEmailWithAll(email);

        Map<Long, List<ReviewImages>> reviewImageMap = getReviewImageMap(reviews);

        Map<Long, List<ReviewConstructionType>> reviewConstructionTypes = getConstructionTypes(reviews);

        return reviews
                .stream()
                .map((review) -> new ReviewResponse(review, review.getCompany(), review.getMember(),
                        reviewImageMap.get(review.getId()), reviewConstructionTypes.get(review.getId()), review.getBuildingType()))
                .toList();
    }
}