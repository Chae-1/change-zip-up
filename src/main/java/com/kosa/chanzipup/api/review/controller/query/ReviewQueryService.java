package com.kosa.chanzipup.api.review.controller.query;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.account.member.QMember.member;
import static com.kosa.chanzipup.domain.buildingtype.QBuildingType.buildingType;
import static com.kosa.chanzipup.domain.constructiontype.QConstructionType.constructionType;
import static com.kosa.chanzipup.domain.estimate.QEstimate.estimate;
import static com.kosa.chanzipup.domain.estimate.QEstimateConstructionType.estimateConstructionType;
import static com.kosa.chanzipup.domain.estimate.QEstimateRequest.estimateRequest;
import static com.kosa.chanzipup.domain.review.QReview.review;
import static com.kosa.chanzipup.domain.review.QReviewConstructionType.reviewConstructionType;
import static com.kosa.chanzipup.domain.review.QReviewImages.reviewImages;
import static com.querydsl.core.types.Projections.constructor;

import com.kosa.chanzipup.api.review.controller.response.ReviewConstructionTypeResponse;
import com.kosa.chanzipup.api.review.controller.response.ReviewImageResponse;
import com.kosa.chanzipup.api.review.controller.response.create.BuildingTypeResponse;
import com.kosa.chanzipup.api.review.controller.response.create.ConstructionTypeResponse;
import com.kosa.chanzipup.api.review.controller.response.create.ReviewCreationPage;
import com.kosa.chanzipup.api.review.controller.response.ReviewDetail;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRepository;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.estimate.Estimate;
import com.kosa.chanzipup.domain.estimate.EstimateRepository;
import com.kosa.chanzipup.domain.estimate.EstimateRequest;
import com.kosa.chanzipup.domain.estimate.EstimateRequestRepository;
import com.kosa.chanzipup.domain.estimate.EstimateStatus;
import com.kosa.chanzipup.domain.estimate.QEstimate;
import com.kosa.chanzipup.domain.estimate.QEstimateConstructionType;
import com.kosa.chanzipup.domain.estimate.QEstimateRequest;
import com.kosa.chanzipup.domain.review.QReviewImages;
import com.kosa.chanzipup.domain.review.Review;
import com.kosa.chanzipup.domain.review.ReviewRepository;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {
    private final JPAQueryFactory factory;
    private final BuildingTypeRepository buildingTypeRepository;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final ReviewRepository reviewRepository;
    private final AccountRepository accountRepository;
    private final EstimateRepository estimateRepository;
    private final EstimateRequestRepository estimateRequestRepository;

    // 리뷰 정보와,
    public ReviewDetail getUserDetail(Long reviewId, UnifiedUserDetails userDetails) {

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

        ConstructorExpression<ReviewDetail> constructor =
                constructor(ReviewDetail.class, review.id.as("reviewId"), review.content, review.title,
                        review.regDate, review.workStartDate,
                        review.workEndDate, review.rating, review.totalPrice, review.floor,
                        company.id.as("companyId"), company.companyName,
                        member.nickName.as("memberNickName"), member.id.as("memberId"),
                        isWriter(userDetails, reviewId), Expressions.asSimple(reviewImageResponses), Expressions.asSimple(reviewConstructionTypeResponses),
                        buildingType.name.as("buildingTypeName"), company.companyLogoUrl, company.address);

        ReviewDetail reviewDetail = factory.select(constructor)
                .from(review)
                .leftJoin(review.company, company)
                .leftJoin(review.member, member)
                .leftJoin(review.buildingType, buildingType)
                .leftJoin(review.reviewImages, QReviewImages.reviewImages)
                .where(review.id.eq(reviewId))
                .fetchFirst();

        return reviewDetail;
    }

    private Expression<Boolean> isWriter(UnifiedUserDetails userDetails, Long reviewId) {
        if (userDetails == null) {
            return Expressions.asBoolean(false);
        }
        Account findMember = accountRepository.findByEmail(userDetails.getName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 정보입니다."));

        Review review = reviewRepository.findByIdWithMember(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰 정보입니다."));

        if (findMember == review.getMember()) {
            return Expressions.asBoolean(true);
        }

        return Expressions.asBoolean(false);
    }

    public ReviewCreationPage reviewCreationPage(Long requestId) {

        List<BuildingTypeResponse> buildingTypeResponses = buildingTypeRepository.findAll()
                .stream()
                .map(BuildingTypeResponse::new)
                .toList();

        List<ConstructionTypeResponse> constructionTypes = constructionTypeRepository.findAll()
                .stream()
                .map(ConstructionTypeResponse::new)
                .toList();

        return new ReviewCreationPage(buildingTypeResponses, constructionTypes);
    }

    public ReviewCreationPage reviewCreationPage2(Long requestId) {

        EstimateRequest reviewRequest = factory.selectFrom(estimateRequest)
                .leftJoin(estimateRequest.buildingType, buildingType).fetchJoin()
                .leftJoin(estimateRequest.constructionTypes, estimateConstructionType).fetchJoin()
                .leftJoin(estimateConstructionType.constructionType, constructionType).fetchJoin()
                .where(estimateRequest.id.eq(requestId))
                .fetchOne();

        Estimate reviewEstimate = factory.selectFrom(estimate)
                .leftJoin(estimate.estimateRequest, estimateRequest)
                .leftJoin(estimate.company, company).fetchJoin()
                .where(estimateRequest.id.eq(requestId), estimate.estimateStatus.eq(EstimateStatus.COMPLETE))
                .fetchOne();


        return new ReviewCreationPage(reviewRequest, reviewEstimate);
    }

}
