package com.kosa.chanzipup.api.review.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import com.kosa.chanzipup.api.review.controller.request.ReviewRegisterRequest;
import com.kosa.chanzipup.api.review.controller.response.*;
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

        return new ReviewRegisterResponse(review.getId());
    }

    // 이전에 올렸던 코드인데 시공시작일과 종료일 필드타입이 바껴서 그에 맞춰 수정했습니다
    // 전체 리뷰 목록 조회
    public List<ReviewResponse> getAllReviews() {
//        List<Review> reviews = reviewRepository.findAllWithAll();
//
//        List<ReviewListResponse> responses = new ArrayList<>();
//        for (Review review : reviews) {
//            List<ReviewImages> reviewImages = review.getReviewImages();
//            List<ReviewImagesForReviewResponse> imageList = reviewImages.stream()
//                    .map(ReviewImagesForReviewResponse::new)
//                    .toList();
//
//            responses.add(new ReviewListResponse(
//                            review.getId(),
//                            review.getTitle(),
//                            review.getContent(),
//                            review.getRegDate(),
//                            review.getWorkStartDate(),
//                            review.getWorkEndDate(),
//                            review.getRating(),
//                            new MemberForReviewResponse(review.getMember()),
//                            new CompanyForReviewResponse(review.getCompany()),
//                            imageList,
//                            new BuildingTypeForReivewResponse(review.getBuildingType()),
//                            new ConstructionTypeForReivewResponse(review.getReviewConstructionTypes()),
//                            review.getTotalPrice(),
//                            review.getFloor()
//                    )
//            );
//        }
//
        List<Review> reviews = reviewRepository.findAllWithAll();

        Map<Long, List<ReviewImages>> reviewImageMap = getReviewImageMap(reviews);

        Map<Long, List<ReviewConstructionType>> reviewConstructionTypes = getConstructionTypes(reviews);


        List<ReviewResponse> reviewResponses = reviews
                .stream()
                .map((review) -> new ReviewResponse(review, review.getCompany(), review.getMember(),
                        reviewImageMap.get(review.getId()), reviewConstructionTypes.get(review.getId()), review.getBuildingType()))
                .toList();

        return reviewResponses;
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
}
