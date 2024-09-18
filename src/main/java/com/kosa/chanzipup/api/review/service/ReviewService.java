package com.kosa.chanzipup.api.review.service;

import com.kosa.chanzipup.api.review.controller.request.ReviewRegisterRequest;
import com.kosa.chanzipup.api.review.controller.response.*;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.review.*;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final ReviewConstructionTypeRepository reviewConstructionTypeRepository;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public ReviewRegisterResponse registerReview(ReviewRegisterRequest request, String email) {

        // 1. 작성자 조회, company 조회, 이후 review 초기 정보 저장.
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Company company = companyRepository.findByCompanyName(request.getCompanyName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파트너입니다."));

        Review review = Review.ofNewReview(request.getTitle(), LocalDateTime.now(), request.getWorkStartDate(),
                request.getWorkEndDate(), request.getRating(), member, company,
                null, null, request.getTotalPrice(), request.getFloor());

        reviewRepository.save(review);


        // 건물 종류 조회해서 저장(하나만 저장하니까)
//        BuildingType buildingType = buildingTypeRepository.findById(request.getBuildingTypeId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid building type ID: " + request.getBuildingTypeId()));
//
//        Review review = Review.ofNewReview(
//                request.getTitle(),
//                request.getContent(),
//                request.getRegDate(),
//                request.getWorkStartDateTime(),
//                request.getWorkEndDateTime(),
//                request.getRating(),
//                member,
//                company,
//                request.getReviewImages(),
//                buildingType,
//                null,   // construnction 무조건 넣어야해서 일단..null이라고 정해두고 뒤에 시공 종류 저장할 때 채워지도록
//                request.getTotalPrice(),
//                request.getFloor()
//        );
//
//        Review savedReview = reviewRepository.save(review);
//
//        // 시공 종류 조회해서 저장(일대다 다대다 관계를 위해서)
//        for (Long constructionTypeId : request.getConstructionService()) {
//            ConstructionType constructionType = constructionTypeRepository.findById(constructionTypeId)
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid construction type ID: " + constructionTypeId));
//
//            ReviewConstructionType reviewConstructionType = new ReviewConstructionType();
//            reviewConstructionType.setReview(savedReview);
//            reviewConstructionType.setConstructionType(constructionType);
//            reviewConstructionTypeRepository.save(reviewConstructionType);
//        }
        return new ReviewRegisterResponse(review.getId());
    }

    // 이전에 올렸던 코드인데 시공시작일과 종료일 필드타입이 바껴서 그에 맞춰 수정했습니다
    // 전체 리뷰 목록 조회
    public List<ReviewListResponse> getAllReviews() {
        // 사진 조회
        List<Review> reviews = reviewRepository.findAllWithAll();

        List<ReviewListResponse> responses = new ArrayList<>();
        for (Review review : reviews) {
            List<ReviewImages> reviewImages = review.getReviewImages();
            List<ReviewImagesForReviewResponse> imageList = reviewImages.stream()
                    .map(ReviewImagesForReviewResponse::new)
                    .toList();

            responses.add(new ReviewListResponse(
                            review.getId(),
                            review.getTitle(),
                            review.getContent(),
                            review.getRegDate(),
                            review.getWorkStartDate(),
                            review.getWorkEndDate(),
                            review.getRating(),
                            new MemberForReviewResponse(review.getMember()),
                            new CompanyForReviewResponse(review.getCompany()),
                            imageList,
                            new BuildingTypeForReivewResponse(review.getBuildingType()),
                            new ConstructionTypeForReivewResponse(review.getReviewConstructionTypes()),
                            review.getTotalPrice(),
                            review.getFloor()
                    )
            );
        }
        return responses;
    }

    @Transactional
    public void updateReviewContent(Long reviewId, String content) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException());

        review.updateContent(content);
    }
}
