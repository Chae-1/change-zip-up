package com.kosa.chanzipup.domain.account.company;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.account.company.QCompanyConstructionType.companyConstructionType;
import static com.kosa.chanzipup.domain.account.member.QMember.member;
import static com.kosa.chanzipup.domain.buildingtype.QBuildingType.buildingType;
import static com.kosa.chanzipup.domain.constructiontype.QConstructionType.constructionType;
import static com.kosa.chanzipup.domain.membership.QMembership.membership;
import static com.kosa.chanzipup.domain.membership.QMembershipType.membershipType;
import static com.kosa.chanzipup.domain.portfolio.QPortfolio.portfolio;
import static com.kosa.chanzipup.domain.portfolio.QPortfolioImage.portfolioImage;
import static com.kosa.chanzipup.domain.review.QReview.review;
import static com.kosa.chanzipup.domain.review.QReviewImages.reviewImages;

import com.kosa.chanzipup.api.company.controller.request.CompanySearchCondition;
import com.kosa.chanzipup.domain.membership.Membership;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.review.Review;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyQueryRepository {
    private final JPAQueryFactory factory;


    public List<Company> findByCityAndDistrictWithConstructionTypes(String city, String district){
        return factory.selectFrom(company)
                .leftJoin(company.constructionTypes, companyConstructionType).fetchJoin()
                .leftJoin(companyConstructionType.constructionType, constructionType).fetchJoin()
                .where(addressLike(city, district))
                .orderBy(company.createdDateTime.asc())
                .fetch();
    }

    public List<Company> findByCityAndDistrictWithMemberships(String city, String district) {
        return factory.selectFrom(company)
                .leftJoin(company.memberships, membership).fetchJoin()
                .leftJoin(membership.membershipType, membershipType).fetchJoin()
                .where(addressLike(city, district))
                .orderBy(company.createdDateTime.asc())
                .fetch();
    }

    public List<Membership> f() {
        return factory.selectFrom(membership)
                .leftJoin(membership.company, company).fetchJoin()
                .leftJoin(membership.membershipType, membershipType).fetchJoin()
                .orderBy(membership.startDateTime.desc())
                .fetch();
    }

    public List<Portfolio> findAllCompanyPortfolios(Long companyId) {
        return factory.selectFrom(portfolio)
                .leftJoin(portfolio.portfolioImages, portfolioImage).fetchJoin()
                .leftJoin(portfolio.buildingType, buildingType).fetchJoin()
                .where(portfolio.account.id.eq(companyId))
                .leftJoin(portfolio)
                .fetch();
    }

    public List<Review> findAllCompanyReviews(Long companyId) {
        return factory.selectFrom(review)
                .leftJoin(review.buildingType, buildingType).fetchJoin()
                .leftJoin(review.reviewImages, reviewImages).fetchJoin()
                .leftJoin(review.member, member).fetchJoin()
                .leftJoin(review.company, company)
                .where(company.id.eq(companyId))
                .fetch();
    }

    private BooleanExpression addressLike(String city, String strict) {

        if (city == null || city.isBlank()) {
            return null;
        }

        if (strict == null || strict.isBlank()) {
            return company.address.like("%" + city + "%");
        }

        return company.address.like("%" + String.format("%s %s", city, strict) + "%");
    }


    public List<CompanyConstructionType> findByConstructionTypesInCompanyIds(List<Long> companyIds) {
        return factory.selectFrom(companyConstructionType)
                .leftJoin(companyConstructionType.company, company).fetchJoin()
                .leftJoin(companyConstructionType.constructionType, constructionType).fetchJoin()
                .where(company.id.in(companyIds))
                .fetch();
    }

    public Company findCompanyByIdWithConstructionTypes(Long companyId) {
        return factory.selectFrom(company)
                .leftJoin(company.constructionTypes, companyConstructionType).fetchJoin()
                .leftJoin(companyConstructionType.constructionType, constructionType).fetchJoin()
                .where(company.id.eq(companyId))
                .fetchOne();
    }
}
