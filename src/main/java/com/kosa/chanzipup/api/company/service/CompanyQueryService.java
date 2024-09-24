package com.kosa.chanzipup.api.company.service;

import com.kosa.chanzipup.api.company.controller.request.CompanySearchCondition;
import com.kosa.chanzipup.api.company.controller.response.CompanyDetailResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyListResponse;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyConstructionType;
import com.kosa.chanzipup.domain.buildingtype.QBuildingType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.membership.*;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyQueryService {

    private final JPAQueryFactory factory;

    public Map<MembershipName, List<CompanyListResponse>> getAllCompanies(CompanySearchCondition searchCondition) {
        log.info("지역 : {}", searchCondition.getCity());
        log.info("구 : {}", searchCondition.getDistrict());
        log.info("서비스 리스트 : {}", searchCondition.getServices());

        List<Company> companyList = factory.select(company)
                .from(company)
                .leftJoin(company.constructionTypes, companyConstructionType).fetchJoin()
                .leftJoin(companyConstructionType.constructionType, constructionType).fetchJoin()
                .where(addressLike(searchCondition.getCity(), searchCondition.getDistrict()))
                .fetch();

        List<Long> constIds = searchCondition.getServices();

        List<Company> filteredCompanies = companyList.stream()
                .filter(company -> {
                    if (constIds == null)
                        return true;

                    List<ConstructionType> constructions = company.getConstructionTypes()
                            .stream()
                            .map(CompanyConstructionType::getConstructionType)
                            .toList();

                    return constructions.stream()
                            .map(ConstructionType::getId)
                            .toList()
                            .containsAll(constIds);
                })
                .toList();

        // companyId 별 membership
        // 1:1인데
        Map<Long, List<Membership>> membershipMap = factory.selectFrom(membership)
                .leftJoin(membership.company, company).fetchJoin()
                .leftJoin(membership.membershipType, membershipType).fetchJoin()
                .orderBy(membership.startDateTime.desc())
                .fetch()
                .stream()
                .collect(groupingBy(membership -> membership.getCompany().getId(), toList()));

        Map<MembershipName, List<CompanyListResponse>> membershipCompany = createMembership();
        filteredCompanies.stream()
                .forEach(company -> {
                    List<Membership> memberships = membershipMap.get(company.getId());
                    if (memberships == null) {
                        membershipCompany.getOrDefault(MembershipName.NO, new ArrayList<>()).add(new CompanyListResponse(company));
                        return;
                    }

                    Membership membership = findMembership(memberships);
                    if (membership.isValid()) {
                        membershipCompany.getOrDefault(membership.getMembershipType().getName(), new ArrayList<>()).add(new CompanyListResponse(company));
                    } else {
                        membershipCompany.getOrDefault(MembershipName.NO, new ArrayList<>()).add(new CompanyListResponse(company));
                    }
                });

        return membershipCompany;
    }

    private Map<MembershipName, List<CompanyListResponse>> createMembership() {
        Map<MembershipName, List<CompanyListResponse>> membershipNameListHashMap = new HashMap<>();
        for (MembershipName name : MembershipName.values()) {
            membershipNameListHashMap.put(name, new ArrayList<>());
        }

        return membershipNameListHashMap;
    }


    private Membership findMembership(List<Membership> memberships) {
        return memberships.get(0);
    }


    public CompanyDetailResponse getCompanyDetailResponse(Long companyId) {

        // 1. 고객들이 작성한 회사의 시공 후기
        List<Review> companyReviewList = factory.selectFrom(review)
                .leftJoin(review.buildingType, buildingType).fetchJoin()
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

        if (strict == null || strict.isBlank()) {
            return company.address.like("%" + city + "%");
        }


        return company.address.like("%" + String.format("%s %s", city, strict) + "%");
    }

    private BooleanExpression constructionTypeIn(List<Long> constructionIds) {

        if (constructionIds == null || constructionIds.isEmpty()) {
            return null;
        }

        return constructionType.id.in(constructionIds);
    }

//    public Map<MembershipName, List<CompanyListResponse>> getAllRecommendCompanies(CompanySearchCondition searchCondition) {
//        return null;
//    }
}
