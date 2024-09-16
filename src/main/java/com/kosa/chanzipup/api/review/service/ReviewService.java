package com.kosa.chanzipup.api.review.service;

import com.kosa.chanzipup.api.review.controller.request.ReviewRegisterRequest;
import com.kosa.chanzipup.api.review.controller.response.*;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.review.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final ReviewConstructionTypeRepository reviewConstructionTypeRepository;
    private final ConstructionTypeRepository constructionTypeRepository;

    // 메서드의 파라미터를 이렇게 지정해도 될까요?그리고 컨트롤러에서 서비스를 불러올 때 파라미터에서 문제가 있어요. 연수님 코드를 많이 참고했습니다.
    // 리뷰 등록
    @Transactional
    public ReviewRegisterResponse registerReview(ReviewRegisterRequest request, Member member, Company company)throws IOException {

        // 건물 종류 조회해서 저장(하나만 저장하니까)
        BuildingType buildingType = buildingTypeRepository.findById(request.getBuildingTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid building type ID: " + request.getBuildingTypeId()));

        Review review = Review.ofNewReview(
                request.getTitle(),
                request.getContent(),
                request.getRegDate(),
                request.getWorkStartDateTime(),
                request.getWorkEndDateTime(),
                request.getRating(),
                member,
                company,
                request.getReviewImages(),
                buildingType,
                null,   // construnction 무조건 넣어야해서 일단..null이라고 정해두고 뒤에 시공 종류 저장할 때 채워지도록
                request.getTotalPrice(),
                request.getFloor()
        );

        Review savedReview = reviewRepository.save(review);

        // 시공 종류 조회해서 저장(일대다 다대다 관계를 위해서)
        for (Long constructionTypeId : request.getConstructionService()) {
            ConstructionType constructionType = constructionTypeRepository.findById(constructionTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid construction type ID: " + constructionTypeId));

            ReviewConstructionType reviewConstructionType = new ReviewConstructionType();
            reviewConstructionType.setReview(savedReview);
            reviewConstructionType.setConstructionType(constructionType);
            reviewConstructionTypeRepository.save(reviewConstructionType);
        }
        return ReviewRegisterResponse.of(savedReview.getId());
    }

    // 전체 리뷰 목록 조회
    public List<ReviewListResponse> getAllReviews() {

        return null;
    }
}
