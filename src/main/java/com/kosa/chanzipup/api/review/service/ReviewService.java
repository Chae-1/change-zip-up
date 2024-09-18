package com.kosa.chanzipup.api.review.service;

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

    // 전체 리뷰 목록 조회
    public List<ReviewListResponse> getAllReviews() {

        return null;
    }

    @Transactional
    public void updateReviewContent(Long reviewId, String content) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException());

        review.updateContent(content);
    }
}
