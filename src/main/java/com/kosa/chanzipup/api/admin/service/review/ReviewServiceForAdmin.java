package com.kosa.chanzipup.api.admin.service.review;

import com.kosa.chanzipup.api.admin.controller.response.review.ReviewDetailResponse;
import com.kosa.chanzipup.api.admin.controller.response.review.ReviewListResponse;
import com.kosa.chanzipup.api.review.controller.response.ReviewConstructionTypeResponse;
import com.kosa.chanzipup.api.review.controller.response.ReviewImageResponse;
import com.kosa.chanzipup.application.Page;
import com.kosa.chanzipup.domain.review.*;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.account.member.QMember.member;
import static com.kosa.chanzipup.domain.buildingtype.QBuildingType.buildingType;
import static com.kosa.chanzipup.domain.constructiontype.QConstructionType.constructionType;
import static com.kosa.chanzipup.domain.review.QReview.review;
import static com.kosa.chanzipup.domain.review.QReviewConstructionType.reviewConstructionType;
import static com.kosa.chanzipup.domain.review.QReviewImages.reviewImages;
import static com.querydsl.core.types.Projections.constructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceForAdmin {

    private final ReviewRepository reviewRepository;

    private final JPAQueryFactory factory;

    private final ReviewConstructionTypeRepository reviewConstructionTypeRepository;

    private final ReviewImagesRepository reviewImagesRepository;

    // 시공 후기 목록 조회
    public Page<List<ReviewListResponse>> getAllReviews(int pageSize, int pageNumber) {
        List<Review> reviews = reviewRepository.findAllWithAll();

        List<ReviewListResponse> reviewListResponses = reviews
                .stream()
                .map((review) -> new ReviewListResponse(review))
                .toList();

        return Page.of(reviewListResponses, pageSize, pageNumber);
    }
    // 시공 후기 상세 조회
    public ReviewDetailResponse getUserDetail(Long reviewId) {

        List<ReviewImageResponse> reviewImageResponses = factory.select(
                        constructor(ReviewImageResponse.class, reviewImages.imageUrl)
                )
                .from(reviewImages)
                .where(reviewImages.review.id.eq(reviewId))
                .fetch();

        List<ReviewConstructionTypeResponse> reviewConstructionTypeResponses = factory
                .select(constructor(ReviewConstructionTypeResponse.class, constructionType.name))
                .from(reviewConstructionType)
                .leftJoin(reviewConstructionType.constructionType, constructionType)
                .where(reviewConstructionType.review.id.eq(reviewId))
                .fetch();

        ConstructorExpression<ReviewDetailResponse> constructor =
                constructor(ReviewDetailResponse.class, review.id.as("reviewId"), review.content, review.title,
                        review.regDate, review.workStartDate,
                        review.workEndDate, review.rating, review.totalPrice, review.floor,
                        company.companyName,
                        member.nickName.as("memberNickName"),
                        Expressions.asSimple(reviewImageResponses), Expressions.asSimple(reviewConstructionTypeResponses),
                        buildingType.name.as("buildingTypeName"));

        ReviewDetailResponse reviewDetail = factory.select(constructor)
                .from(review)
                .leftJoin(review.company, company)
                .leftJoin(review.member, member)
                .leftJoin(review.buildingType, buildingType)
                .leftJoin(review.reviewImages, QReviewImages.reviewImages)
                .where(review.id.eq(reviewId))
                .fetchFirst();

        return reviewDetail;
    }
    // 시공 후기 삭제
    @Transactional
    public List<String> deleteReview(Long reviewId) {
        Review review = reviewRepository.findByIdAndUserEmailForAdmin(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("삭제 할 수 없습니다."));

        List<String> imageUrls = review.getReviewImages()
                .stream()
                .map(ReviewImages::getImageUrl)
                .toList();

        reviewConstructionTypeRepository.deleteByReviewId(review.getId());
        reviewRepository.deleteById(reviewId);
        reviewImagesRepository.deleteByReviewId(review.getId());

        return imageUrls;
    }
}
