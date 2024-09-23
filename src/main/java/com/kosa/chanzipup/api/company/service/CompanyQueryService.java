package com.kosa.chanzipup.api.company.service;

import com.kosa.chanzipup.api.company.controller.request.CompanySearchCondition;
import com.kosa.chanzipup.api.company.controller.response.CompanyDetailResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyListResponse;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.buildingtype.QBuildingType;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.portfolio.QPortfolio;
import com.kosa.chanzipup.domain.portfolio.QPortfolioImage;
import com.kosa.chanzipup.domain.review.QReview;
import com.kosa.chanzipup.domain.review.QReviewImages;
import com.kosa.chanzipup.domain.review.Review;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.account.company.QCompanyConstructionType.companyConstructionType;
import static com.kosa.chanzipup.domain.account.member.QMember.member;
import static com.kosa.chanzipup.domain.buildingtype.QBuildingType.buildingType;
import static com.kosa.chanzipup.domain.constructiontype.QConstructionType.constructionType;
import static com.kosa.chanzipup.domain.portfolio.QPortfolio.portfolio;
import static com.kosa.chanzipup.domain.portfolio.QPortfolioImage.portfolioImage;
import static com.kosa.chanzipup.domain.review.QReview.review;
import static com.kosa.chanzipup.domain.review.QReviewImages.reviewImages;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyQueryService {

    private final JPAQueryFactory factory;

    // todo: 멤버십에 따른 분류를 수행해야한다.
    public List<CompanyListResponse> getAllCompanies(CompanySearchCondition searchCondition) {
        log.info("지역 : {}", searchCondition.getCity());
        log.info("구 : {}", searchCondition.getDistrict());
        log.info("서비스 리스트 : {}", searchCondition.getServices());

        List<Company> companyList = factory.select(company)
                .from(company)
                .leftJoin(company.constructionTypes, companyConstructionType).fetchJoin()
                .leftJoin(companyConstructionType.constructionType, constructionType).fetchJoin()
                .where(constructionTypeIn(searchCondition.getServices()),
                        addressLike(searchCondition.getCity(), searchCondition.getDistrict()))
                .fetch();

        return companyList.stream()
                .map(company -> new CompanyListResponse(company))
                .toList();
    }


    public CompanyDetailResponse getCompanyDetailResponse(Long companyId) {

        // 1. 고객들이 작성한 회사의 시공 후기
        List<Review> companyReviewList = factory.selectFrom(review)
                .leftJoin(review.reviewImages, reviewImages).fetchJoin()
                .leftJoin(review.member, member).fetchJoin()
                .leftJoin(review.company, company)
                .where(company.id.eq(companyId))
                .fetch();

        // 2. 업체 수행한 시공 사례
        List<Portfolio> companyPortfolioList = factory.selectFrom(portfolio)
                .leftJoin(portfolio.portfolioImages, portfolioImage).fetchJoin()
                .leftJoin(portfolio.buildingType, buildingType).fetchJoin()
                .where(portfolio.account.id.eq(companyId))
                .leftJoin(portfolio)
                .fetch();

        // 3. 회사 정보
        Company findCompany = factory.select(company)
                .from(company)
                .leftJoin(company.constructionTypes, companyConstructionType).fetchJoin()
                .leftJoin(companyConstructionType.constructionType, constructionType).fetchJoin()
                .where(company.id.eq(companyId))
                .fetchOne();

        // 4.
        return new CompanyDetailResponse(
                companyReviewList,
                companyPortfolioList,
                findCompany
        );

    }


    private BooleanExpression addressLike(String city, String strict) {

        if (city == null || city.isBlank()) {
            return null;
        }

        String fullAddress = String.format("%s %s", city, strict);

        return company.address.like("%" + fullAddress + "%");
    }

    private BooleanExpression constructionTypeIn(List<Long> constructionIds) {

        if (constructionIds == null || constructionIds.isEmpty()) {
            return null;
        }

        return constructionType.id.in(constructionIds);
    }
}
